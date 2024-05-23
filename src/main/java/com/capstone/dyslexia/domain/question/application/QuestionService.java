package com.capstone.dyslexia.domain.question.application;

import com.capstone.dyslexia.domain.level.application.LevelRangeService;
import com.capstone.dyslexia.domain.member.application.MemberService;
import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.question.domain.sentence.QuestionSentence;
import com.capstone.dyslexia.domain.question.domain.sentence.repository.QuestionSentenceRepository;
import com.capstone.dyslexia.domain.question.domain.word.QuestionWord;
import com.capstone.dyslexia.domain.question.domain.word.repository.QuestionWordRepository;
import com.capstone.dyslexia.domain.question.dto.response.QuestionResponseDto;
import com.capstone.dyslexia.global.error.exceptions.InternalServerException;
import com.capstone.dyslexia.global.error.exceptions.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<QuestionResponseDto.GetRandom> getRandomQuestionList(Long memberId, Long numOfQuestions) {
        Member member = memberService.memberValidation(memberId);

        EnumMap<QuestionResponseType, Double> probabilities = levelRangeService.getQuestionResponseProbability(member)
                .orElseThrow(() -> new InternalServerException(INTERNAL_SERVER, "사용자 레벨에 대한 확률 set이 존재하지 않습니다. 관리자에게 문의 바랍니다."));

        List<QuestionResponseDto.GetRandom> questionResponseDtoList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numOfQuestions; i++) {
            QuestionResponseType questionResponseType = getRandomQuestionType(probabilities, random);

            if (questionResponseType.equals(READ_SENTENCE)) {
                QuestionSentence question = getRandomQuestionSentence();
                questionResponseDtoList.add(new QuestionResponseDto.GetRandom(question));
            } else {
                QuestionWord question = getRandomQuestionWord();
                questionResponseDtoList.add(new QuestionResponseDto.GetRandom(question, questionResponseType));
            }
        }

        if (questionResponseDtoList.isEmpty()) {
            throw new ServiceUnavailableException(DATA_NOT_EXIEST, "해당 문제 타입에 대한 데이터가 존재하지 않습니다.");
        }

        return questionResponseDtoList;
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
        Long qty = questionWordRepository.count();
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
        Long qty = questionSentenceRepository.count();
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
