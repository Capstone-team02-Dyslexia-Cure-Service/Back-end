package com.capstone.dyslexia.domain.question.application;

import com.capstone.dyslexia.domain.level.application.LevelRangeService;
import com.capstone.dyslexia.domain.member.application.MemberService;
import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.member.domain.respository.MemberRepository;
import com.capstone.dyslexia.domain.question.domain.Question;
import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.question.domain.QuestionType;
import com.capstone.dyslexia.domain.question.domain.respository.QuestionRepository;
import com.capstone.dyslexia.domain.question.dto.request.CreateQuestionRequestDto;
import com.capstone.dyslexia.domain.question.dto.request.QuestionListRequestDto;
import com.capstone.dyslexia.domain.question.dto.response.CreateQuestionResponseDto;
import com.capstone.dyslexia.domain.question.dto.response.QuestionResponseDto;
import com.capstone.dyslexia.global.error.exceptions.BadRequestException;
import com.capstone.dyslexia.global.error.exceptions.InternalServerException;
import com.capstone.dyslexia.global.error.exceptions.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.capstone.dyslexia.domain.question.domain.QuestionResponseType.READ_SENTENCE;
import static com.capstone.dyslexia.domain.question.domain.QuestionType.SENTENCE;
import static com.capstone.dyslexia.domain.question.domain.QuestionType.WORD;
import static com.capstone.dyslexia.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final MemberService memberService;
    private final LevelRangeService levelRangeService;

    @Transactional
    public CreateQuestionResponseDto create(Long memberId, CreateQuestionRequestDto createQuestionRequestDto) {
        memberService.memberValidation(memberId);

        Question question = Question.builder()
                .questionType(createQuestionRequestDto.getQuestionType())
                .content(createQuestionRequestDto.getContent())
                .pronunciationFilePath(createQuestionRequestDto.getPronunciationFilePath())
                .videoPath(createQuestionRequestDto.getVideoPath())
                .build();

        questionRepository.save(question);


        return CreateQuestionResponseDto.builder()
                .id(question.getId())
                .questionType(question.getQuestionType())
                .content(question.getContent())
                .pronunciationFilePath(question.getPronunciationFilePath())
                .videoPath(question.getVideoPath())
                .build();
    }

    public List<CreateQuestionResponseDto> getQuestionById(Long memberId, QuestionListRequestDto questionListRequestDto) {
        memberService.memberValidation(memberId);

        List<CreateQuestionResponseDto> createQuestionResponseDtoList = new ArrayList<>();

        for (Long questionId : questionListRequestDto.getQuestionIdList()) {
            Question question = questionRepository.findById(questionId)
                    .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "잘못된 Member ID 입니다."));

            CreateQuestionResponseDto createQuestionResponseDto = CreateQuestionResponseDto.builder()
                    .id(question.getId())
                    .questionType(question.getQuestionType())
                    .content(question.getContent())
                    .pronunciationFilePath(question.getPronunciationFilePath())
                    .videoPath(question.getVideoPath())
                    .build();

            createQuestionResponseDtoList.add(createQuestionResponseDto);
        }
        return createQuestionResponseDtoList;
    }

    public List<QuestionResponseDto> getRandomQuestionList(Long memberId, Long numOfQuestions) {
        Member member = memberService.memberValidation(memberId);

        Optional<EnumMap<QuestionResponseType, Double>> optionalProbabilities = levelRangeService.getQuestionResponseProbability(member);
        EnumMap<QuestionResponseType, Double> probabilities = optionalProbabilities.orElseThrow(() ->
                new IllegalArgumentException("No probabilities found for the member's level."));

        List<QuestionResponseDto> questionResponseDtoList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numOfQuestions; i++) {
            QuestionResponseType questionType = getRandomQuestionType(probabilities, random);
            Question question = getQuestionByType(questionType);

            QuestionResponseDto questionResponseDto = QuestionResponseDto.builder()
                    .id(question.getId())
                    .questionType(question.getQuestionType())
                    .content(question.getContent())
                    .pronunciationFilePath(question.getPronunciationFilePath())
                    .videoPath(question.getVideoPath())
                    .questionResponseType(questionType)
                    .build();

            questionResponseDtoList.add(questionResponseDto);
        }

        if (questionResponseDtoList.isEmpty()) {
            throw new ServiceUnavailableException(DATA_NOT_EXIEST, "해당 문제 타입에 대한 데이터가 존재하지 않습니다.");
        }

        return questionResponseDtoList;
    }

    private QuestionResponseType getRandomQuestionType(EnumMap<QuestionResponseType, Double> probabilities, Random random) {
        double randomValue = random.nextDouble() * 100;
        double cumulativeProbability = 0.0;

        for (QuestionResponseType type : probabilities.keySet()) {
            cumulativeProbability += probabilities.get(type);
            if (randomValue <= cumulativeProbability) {
                return type;
            }
        }

        throw new InternalServerException(INTERNAL_SERVER, "QuestionResponseType 생성을 실패했습니다.");
    }

    private Question getQuestionByType(QuestionResponseType questionResponseType) {
        QuestionType questionType = (questionResponseType == READ_SENTENCE) ? SENTENCE : WORD;
        Long qty = questionRepository.countByQuestionType(questionType);
        if (qty <= 0) {
            throw new ServiceUnavailableException(DATA_NOT_EXIEST, "해당 문제 타입에 대한 데이터가 존재하지 않습니다.");
        }

        int indexOffset = (int) (Math.random() * qty);
        return questionRepository.findAllByQuestionType(questionType, PageRequest.of(indexOffset, 1))
                .stream()
                .findFirst()
                .orElseThrow(() -> new ServiceUnavailableException(DATA_NOT_EXIEST, "해당 문제 타입에 대한 데이터가 존재하지 않습니다."));
    }

}
