package com.capstone.dyslexia.domain.solvingRecord.application;

import com.capstone.dyslexia.domain.member.application.MemberService;
import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.question.domain.Question;
import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.question.domain.respository.QuestionRepository;
import com.capstone.dyslexia.domain.solvingRecord.domain.SolvingRecord;
import com.capstone.dyslexia.domain.solvingRecord.domain.respository.SolvingRecordRepository;
import com.capstone.dyslexia.domain.solvingRecord.dto.ml.GradeMLRequest;
import com.capstone.dyslexia.domain.solvingRecord.dto.ml.GradeMLResponse;
import com.capstone.dyslexia.domain.solvingRecord.dto.response.SolvingRecordCreateResponseDto;
import com.capstone.dyslexia.domain.solvingRecord.dto.request.SolvingRecordRequestDto;
import com.capstone.dyslexia.domain.solvingRecord.dto.response.SolvingRecordResponseDto;
import com.capstone.dyslexia.global.error.exceptions.BadRequestException;
import com.capstone.dyslexia.global.error.exceptions.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.capstone.dyslexia.domain.question.domain.QuestionResponseType.*;
import static com.capstone.dyslexia.global.error.ErrorCode.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SolvingRecordService {

    private final SolvingRecordRepository solvingRecordRepository;
    private final QuestionRepository questionRepository;
    private final MemberService memberService;


    public SolvingRecordResponseDto findSolvingRecordById(Long memberId, Long solvingRecordId) {
        memberService.memberValidation(memberId);

        SolvingRecord solvingRecord = solvingRecordRepository.findById(solvingRecordId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "해당 문제 풀이 기록이 존재하지 않습니다."));

        return SolvingRecordResponseDto.builder()
                .id(solvingRecord.getId())
                .isCorrect(solvingRecord.getIsCorrect())
                .questionId(solvingRecord.getQuestion().getId())
                .build();
    }


    public List<SolvingRecordCreateResponseDto> createSolvingRecordList(Long memberId, List<SolvingRecordRequestDto> solvingRecordListRequestDto) {
        Member member = memberService.memberValidation(memberId);

        List<SolvingRecordCreateResponseDto> solvingRecordCreateResponseDtoList = new ArrayList<>();

        for (SolvingRecordRequestDto solvingRecordDto : solvingRecordListRequestDto) {
            Question question = questionRepository.findById(solvingRecordDto.getQuestionId())
                    .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "유효하지 않은 Question ID 입니다."));
            QuestionResponseType questionResponseType = solvingRecordDto.getQuestionResponseType();

            SolvingRecordCreateResponseDto solvingRecordCreateResponseDto;

            if (questionResponseType.equals(SELECT_WORD) || questionResponseType.equals(WRITE_WORD)) {
                solvingRecordCreateResponseDto = gradeQuestion(member, question, solvingRecordDto.getAnswer());
            } else if (questionResponseType.equals(READ_WORD) || questionResponseType.equals(READ_SENTENCE)) {
                solvingRecordCreateResponseDto = gradeQuestion(member, question, solvingRecordDto.getAnswerMultipartFile());
            } else {
                throw new BadRequestException(INVALID_PARAMETER, "유효하지 않은 Question Response Type 입니다.");
            }

            solvingRecordCreateResponseDtoList.add(solvingRecordCreateResponseDto);
        }

        if (solvingRecordCreateResponseDtoList.isEmpty()) {
            throw new InternalServerException(INTERNAL_SERVER, "Solving Record 생성 및 Question 정답 유무 생성에 문제 발생. 서버 관리자에게 문의 바랍니다.");
        }

        return solvingRecordCreateResponseDtoList;
    }

    @Transactional
    protected SolvingRecordCreateResponseDto gradeQuestion(Member member, Question question, String answer) {
        Boolean isCorrect;
        if (question.getContent().equals(answer)) {
            isCorrect = TRUE;
        } else {
            isCorrect = FALSE;
        }

        SolvingRecord solvingRecord = SolvingRecord.builder()
                .isCorrect(isCorrect)
                .member(member)
                .question(question)
                .build();

        solvingRecordRepository.save(solvingRecord);

        return SolvingRecordCreateResponseDto.builder()
                .questionId(solvingRecord.getQuestion().getId())
                .isCorrect(solvingRecord.getIsCorrect())
                .answer(question.getContent())
                .build();
    }

    @Transactional
    protected SolvingRecordCreateResponseDto gradeQuestion(Member member, Question question, MultipartFile answerFile) {

        GradeMLResponse gradeMLResponse = gradeMLServer(GradeMLRequest.builder()
                .answerFile(answerFile)
                .questionSavedAnswerFilePath(question.getPronunciationFilePath())
                .build());

        Boolean isCorrect = gradeMLResponse.getIsCorrect();

        SolvingRecord solvingRecord = SolvingRecord.builder()
                .isCorrect(isCorrect)
                .member(member)
                .question(question)
                .build();

        solvingRecordRepository.save(solvingRecord);

        return SolvingRecordCreateResponseDto.builder()
                .questionId(solvingRecord.getQuestion().getId())
                .isCorrect(solvingRecord.getIsCorrect())
                .answer(question.getContent())
                .build();

    }

    // TODO : ML 통신 구현
    private GradeMLResponse gradeMLServer(GradeMLRequest gradeMLRequest) {
        GradeMLResponse gradeMLResponse = new GradeMLResponse();
        return gradeMLResponse;
    }
}
