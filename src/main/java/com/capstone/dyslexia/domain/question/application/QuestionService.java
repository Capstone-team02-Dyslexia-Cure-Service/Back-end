package com.capstone.dyslexia.domain.question.application;

import com.capstone.dyslexia.domain.level.application.LevelRangeService;
import com.capstone.dyslexia.domain.member.application.MemberService;
import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.question.domain.Question;
import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.question.domain.sentence.QuestionSentence;
import com.capstone.dyslexia.domain.question.domain.sentence.repository.QuestionSentenceRepository;
import com.capstone.dyslexia.domain.question.domain.word.QuestionWord;
import com.capstone.dyslexia.domain.question.domain.word.repository.QuestionWordRepository;
import com.capstone.dyslexia.domain.question.dto.response.QuestionResponseDto;
import com.capstone.dyslexia.domain.uuidFile.application.UUIDFileService;
import com.capstone.dyslexia.domain.uuidFile.domain.UUIDFile;
import com.capstone.dyslexia.global.error.exceptions.InternalServerException;
import com.capstone.dyslexia.global.error.exceptions.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.capstone.dyslexia.domain.question.domain.QuestionResponseType.READ_SENTENCE;
import static com.capstone.dyslexia.global.error.ErrorCode.DATA_NOT_EXIEST;
import static com.capstone.dyslexia.global.error.ErrorCode.INTERNAL_SERVER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final MemberService memberService;
    private final LevelRangeService levelRangeService;
    private final QuestionWordRepository questionWordRepository;
    private final QuestionSentenceRepository questionSentenceRepository;
    private final UUIDFileService uuidFileService;

    @Transactional
    public QuestionResponseDto.CreateWord createWord(String content, MultipartFile videoFile) {
        QuestionWord questionWord = QuestionWord.builder()
                .content(content)
                .videoPath(uuidFileService.saveVideo(videoFile).getFileUrl())
                .build();

        questionWordRepository.save(questionWord);

        return QuestionResponseDto.CreateWord.from(questionWord);
    }

    @Transactional
    public QuestionResponseDto.CreateSentence createSentence(String content, MultipartFile pronunciationFile, MultipartFile videoFile) {
        QuestionSentence questionSentence = QuestionSentence.builder()
                .content(content)
                .pronunciationFilePath(uuidFileService.savePronunciation(pronunciationFile).getFileUrl())
                .videoPath(uuidFileService.saveVideo(videoFile).getFileUrl())
                .build();

        questionSentenceRepository.save(questionSentence);

        return QuestionResponseDto.CreateSentence.from(questionSentence);
    }

    public QuestionResponseDto.Find getQuestionById(Long memberId, Long questionRequestDto) {
        Member member = memberService.memberValidation(memberId);

        QuestionWord questionWord = questionWordRepository.findById(questionRequestDto)
                .orElseThrow(() -> new ServiceUnavailableException(DATA_NOT_EXIEST, "해당 문제 타입에 대한 데이터가 존재하지 않습니다."));

        return QuestionResponseDto.Find.from(questionWord);
    }

    public List<QuestionResponseDto.GetRandom> getRandomQuestionList(Long memberId, Long numOfQuestions) {
        Member member = memberService.memberValidation(memberId);

        List<QuestionResponseDto.GetRandom> questionResponseDtoList = new ArrayList<>();

        Map<Question, QuestionResponseType> questionMap = randomQuestionListBuilder(member, numOfQuestions);

        while (!questionMap.isEmpty()) {
            Question question = questionMap.keySet().iterator().next();
            QuestionResponseType questionResponseType = questionMap.get(question);
            questionMap.remove(question);
            questionResponseDtoList.add(
                    question instanceof QuestionWord ?
                            QuestionResponseDto.GetRandom.from((QuestionWord) question, questionResponseType) :
                            QuestionResponseDto.GetRandom.from((QuestionSentence) question)
            );
        }
        return questionResponseDtoList;
    }

    public List<QuestionResponseDto.Find> getRandomQuestionEduList(Long memberId, Long numOfQuestions) {
        Member member = memberService.memberValidation(memberId);

        List<QuestionResponseDto.Find> questionResponseDtoList = new ArrayList<>();

        Map<Question, QuestionResponseType> questionMap = randomQuestionListBuilder(member, numOfQuestions);

        while (!questionMap.isEmpty()) {
            Question question = questionMap.keySet().iterator().next();
            questionMap.remove(question);
            questionResponseDtoList.add(
                    question instanceof QuestionWord ?
                            QuestionResponseDto.Find.from((QuestionWord) question) :
                            QuestionResponseDto.Find.from((QuestionSentence) question)
            );
        }

        return questionResponseDtoList;
    }

    public Map<Question, QuestionResponseType> randomQuestionListBuilder(Member member, Long numOfQuestions) {
        EnumMap<QuestionResponseType, Double> probabilities = levelRangeService.getQuestionResponseProbability(member)
                .orElseThrow(() -> new InternalServerException(INTERNAL_SERVER, "사용자 레벨에 대한 확률 set이 존재하지 않습니다. 관리자에게 문의 바랍니다."));

        Map<Question, QuestionResponseType> questionMap = new HashMap<>();
        Random random = new Random();

        List<QuestionResponseType> questionResponseTypeList = new ArrayList<>();
        for (int i = 0; i < numOfQuestions; i++) {
            questionResponseTypeList.add(getRandomQuestionType(probabilities, random));
        }

        long numOfReadSentence = questionResponseTypeList.stream().filter(type -> type.equals(READ_SENTENCE)).count();

        if (
                numOfReadSentence > questionSentenceRepository.count() ||
                        questionResponseTypeList.size() - numOfQuestions > questionWordRepository.count()
        ) {
            throw new InternalServerException(INTERNAL_SERVER, "문제 데이터가 부족합니다. 관리자에게 문의 바랍니다.");
        }

        for (int i = 0; i < numOfQuestions; i++) {
            QuestionResponseType questionResponseType = getRandomQuestionType(probabilities, random);
            Question question = questionResponseType.equals(READ_SENTENCE) ? getRandomQuestionSentence() : getRandomQuestionWord();

            if (questionMap.containsKey(question)) {
                i--;
                continue;
            }

            questionMap.put(
                    questionResponseType.equals(READ_SENTENCE) ? getRandomQuestionSentence() : getRandomQuestionWord(),
                    questionResponseType
            );
        }
        if (questionMap.isEmpty()) {
            throw new ServiceUnavailableException(DATA_NOT_EXIEST, "해당 문제 타입에 대한 데이터가 존재하지 않습니다.");
        }
        return questionMap;
    }

    private QuestionResponseType getRandomQuestionType(EnumMap<QuestionResponseType, Double> probabilities, Random random) {
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0.0;

        for (Map.Entry<QuestionResponseType, Double> entry : probabilities.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (randomValue <= cumulativeProbability) {
                return entry.getKey();
            }
        }

        throw new InternalServerException(INTERNAL_SERVER, "QuestionResponseType 생성을 실패했습니다.");
    }

    private QuestionWord getRandomQuestionWord() {
        long qty = questionWordRepository.count();
        if (qty <= 0) {
            throw new ServiceUnavailableException(DATA_NOT_EXIEST, "해당 문제 타입에 대한 데이터가 존재하지 않습니다.");
        }

        int indexOffset = (int) (Math.random() * qty);
        return questionWordRepository.findRandomQuestions(PageRequest.of(indexOffset, 1))
                .stream()
                .findFirst()
                .orElseThrow(() -> new ServiceUnavailableException(DATA_NOT_EXIEST, "해당 문제 타입에 대한 데이터가 존재하지 않습니다."));
    }

    private QuestionSentence getRandomQuestionSentence() {
        long qty = questionSentenceRepository.count();
        if (qty <= 0) {
            throw new ServiceUnavailableException(DATA_NOT_EXIEST, "해당 문제 타입에 대한 데이터가 존재하지 않습니다.");
        }

        int indexOffset = (int) (Math.random() * qty);
        return questionSentenceRepository.findRandomQuestions(PageRequest.of(indexOffset, 1))
                .stream()
                .findFirst()
                .orElseThrow(() -> new ServiceUnavailableException(DATA_NOT_EXIEST, "해당 문제 타입에 대한 데이터가 존재하지 않습니다."));
    }

}
