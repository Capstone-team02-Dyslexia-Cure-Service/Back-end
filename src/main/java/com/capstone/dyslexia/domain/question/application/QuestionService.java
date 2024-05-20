package com.capstone.dyslexia.domain.question.application;

import com.capstone.dyslexia.domain.member.application.MemberService;
import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.member.domain.respository.MemberRepository;
import com.capstone.dyslexia.domain.question.domain.Question;
import com.capstone.dyslexia.domain.question.domain.respository.QuestionRepository;
import com.capstone.dyslexia.domain.question.dto.request.CreateQuestionRequestDto;
import com.capstone.dyslexia.domain.question.dto.request.QuestionListRequestDto;
import com.capstone.dyslexia.domain.question.dto.response.CreateQuestionResponseDto;
import com.capstone.dyslexia.domain.question.dto.response.QuestionResponseDto;
import com.capstone.dyslexia.global.error.exceptions.BadRequestException;
import com.capstone.dyslexia.global.error.exceptions.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.capstone.dyslexia.global.error.ErrorCode.DATA_NOT_EXIEST;
import static com.capstone.dyslexia.global.error.ErrorCode.ROW_DOES_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final MemberService memberService;

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

        List<QuestionResponseDto> questionResponseDtoList = new ArrayList<>();

        Long QuestionDBSize = questionRepository.count();




        /*
        for (QuestionResponseType questionResponseType : randomQuestionRequestDto.getResponseTypeList()) {
            QuestionType questionType = questionResponseType.equals(READ_SENTENCE) ? SENTENCE : WORD;

            Long qty = questionRepository.countByQuestionType(questionType);
            if (qty < 0) throw new ServiceUnavailableException(DATA_NOT_EXIEST, "해당 문제 타입에 대한 데이터가 존재하지 않습니다.");
            Integer indexOffset = (int) (Math.random() * qty);


            Page<Question> questionPage = questionRepository
                    .findAllByQuestionType(WORD, PageRequest.of(indexOffset, 1)
                    );

            if (!questionPage.hasContent()) throw new ServiceUnavailableException(DATA_NOT_EXIEST, "해당 문제 타입에 대한 데이터가 존재하지 않습니다.");
            Question question = questionPage.getContent().get(0);

            QuestionResponseDto questionResponseDto = QuestionResponseDto.builder()
                    .id(question.getId())
                    .questionType(question.getQuestionType())
                    .content(question.getContent())
                    .pronunciationFilePath(question.getPronunciationFilePath())
                    .videoPath(question.getVideoPath())
                    .questionResponseType(questionResponseType)
                    .build();

            questionResponseDtoList.add(questionResponseDto);
        }

         */

        if (questionResponseDtoList.isEmpty()) throw new ServiceUnavailableException(DATA_NOT_EXIEST, "해당 문제 타입에 대한 데이터가 존재하지 않습니다.");

        return questionResponseDtoList;
    }



}
