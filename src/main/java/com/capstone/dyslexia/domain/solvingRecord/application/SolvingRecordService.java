package com.capstone.dyslexia.domain.solvingRecord.application;

import com.capstone.dyslexia.domain.dateAchievement.application.DateAchievementService;
import com.capstone.dyslexia.domain.member.application.MemberService;
import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.question.domain.sentence.QuestionSentence;
import com.capstone.dyslexia.domain.question.domain.sentence.repository.QuestionSentenceRepository;
import com.capstone.dyslexia.domain.question.domain.word.QuestionWord;
import com.capstone.dyslexia.domain.question.domain.word.repository.QuestionWordRepository;
import com.capstone.dyslexia.domain.solvingRecord.domain.SolvingRecord;
import com.capstone.dyslexia.domain.solvingRecord.domain.repository.SolvingRecordRepository;
import com.capstone.dyslexia.domain.solvingRecord.dto.ml.GradeMLRequest;
import com.capstone.dyslexia.domain.solvingRecord.dto.ml.GradeMLResponse;
import com.capstone.dyslexia.domain.solvingRecord.dto.request.SolvingRecordRequestDto;
import com.capstone.dyslexia.domain.solvingRecord.dto.response.SolvingRecordResponseDto;
import com.capstone.dyslexia.domain.uuidFile.application.UUIDFileService;
import com.capstone.dyslexia.domain.uuidFile.domain.UUIDFile;
import com.capstone.dyslexia.global.error.exceptions.BadRequestException;
import com.capstone.dyslexia.global.error.exceptions.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final MemberService memberService;

    private final QuestionWordRepository questionWordRepository;

    private final UUIDFileService uuidFileService;
    private final QuestionSentenceRepository questionSentenceRepository;
    private final DateAchievementService dateAchievementService;


    public SolvingRecordResponseDto.Find findSolvingRecordById(Long memberId, Long solvingRecordId) {
        memberService.memberValidation(memberId);

        SolvingRecord solvingRecord = solvingRecordRepository.findById(solvingRecordId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "해당 문제 풀이 기록이 존재하지 않습니다."));


        if (solvingRecord.getQuestionResponseType().equals(READ_SENTENCE)) {
            QuestionSentence questionSentence = solvingRecord.getQuestionSentence();
            return new SolvingRecordResponseDto.Find(solvingRecord, questionSentence);
        } else {
            QuestionWord questionWord = solvingRecord.getQuestionWord();
            return new SolvingRecordResponseDto.Find(solvingRecord, questionWord);
        }
    }


    public List<SolvingRecordResponseDto.Create> createSolvingRecordList(Long memberId, List<SolvingRecordRequestDto.Convert> solvingRecordRequestConvertDtoList) {
        Member member = memberService.memberValidation(memberId);

        List<SolvingRecordResponseDto.Create> solvingRecordCreateResponseDtoList = new ArrayList<>();

        for (SolvingRecordRequestDto.Convert requestDto : solvingRecordRequestConvertDtoList) {
            if (requestDto.getQuestionResponseType().equals(SELECT_WORD) || requestDto.getQuestionResponseType().equals(WRITE_WORD)) {
                solvingRecordCreateResponseDtoList.add(gradeQuestionWord(member, new SolvingRecordRequestDto.CreateString(requestDto)));
            } else if (requestDto.getQuestionResponseType().equals(READ_WORD)) {
                solvingRecordCreateResponseDtoList.add(gradeQuestionWord(member, new SolvingRecordRequestDto.CreateFile(requestDto)));
            } else if (requestDto.getQuestionResponseType().equals(READ_SENTENCE)) {
                solvingRecordCreateResponseDtoList.add(gradeQuestionSentence(member, new SolvingRecordRequestDto.CreateFile(requestDto)));
            }
            else throw new BadRequestException(INVALID_PARAMETER, "유효하지 않은 Question Response Type 입니다.");
        }

        if (solvingRecordCreateResponseDtoList.isEmpty()) {
            throw new InternalServerException(INTERNAL_SERVER, "Solving Record 생성 및 Question 정답 유무 생성에 문제 발생. 서버 관리자에게 문의 바랍니다.");
        }

        return solvingRecordCreateResponseDtoList;
    }

    // SELECT_WORD, WRITE WORD
    @Transactional
    protected SolvingRecordResponseDto.Create gradeQuestionWord(Member member, SolvingRecordRequestDto.CreateString requestDto) {
        QuestionWord questionWord = questionWordRepository.findById(requestDto.getQuestionId())
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 Word 문제 ID(PK) 입니다."));

        Boolean isCorrect = (questionWord.getContent().equals(requestDto.getAnswer()) ? TRUE : FALSE);

        SolvingRecord solvingRecord = SolvingRecord.builder()
                .isCorrect(isCorrect)
                .submissionAnswer(requestDto.getAnswer())
                .questionResponseType(requestDto.getQuestionResponseType())
                .member(member)
                .questionWord(questionWord)
                .build();

        solvingRecordRepository.save(solvingRecord);

        dateAchievementService.addSolvingRecord(solvingRecord);

        return SolvingRecordResponseDto.Create.builder()
                .questionWordId(questionWord.getId())
                .isCorrect(isCorrect)
                .questionResponseType(solvingRecord.getQuestionResponseType())
                .answer(questionWord.getContent())
                .submissionAnswer(solvingRecord.getSubmissionAnswer())
                .build();
    }

    // READ_WORD
    @Transactional
    protected SolvingRecordResponseDto.Create gradeQuestionWord(Member member, SolvingRecordRequestDto.CreateFile requestDto) {
        QuestionWord questionWord = questionWordRepository.findById(requestDto.getQuestionId())
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 Word 문제 ID(PK) 입니다."));

        UUIDFile uuidFile = uuidFileService.saveSubmission(member, requestDto.getAnswerFile());


        GradeMLResponse gradeMLResponse = gradeMLServer(GradeMLRequest.Word.builder()
                .submissionAnswerFilePath(uuidFile.getUuid())
                .questionContent(questionWord.getContent())
                .build());

        SolvingRecord solvingRecord = SolvingRecord.builder()
                .isCorrect(gradeMLResponse.getIsCorrect())
                .submissionAnswer(uuidFile.getFileUrl())
                .questionResponseType(requestDto.getQuestionResponseType())
                .member(member)
                .questionWord(questionWord)
                .build();

        solvingRecordRepository.save(solvingRecord);

        dateAchievementService.addSolvingRecord(solvingRecord);

        return SolvingRecordResponseDto.Create.builder()
                .questionWordId(questionWord.getId())
                .isCorrect(gradeMLResponse.getIsCorrect())
                .questionResponseType(solvingRecord.getQuestionResponseType())
                .answer(questionWord.getContent())
                .submissionAnswer(solvingRecord.getSubmissionAnswer())
                .build();
    }

    // READ_SENTENCE
    @Transactional
    protected SolvingRecordResponseDto.Create gradeQuestionSentence(Member member, SolvingRecordRequestDto.CreateFile requestDto) {
        QuestionSentence questionSentence = questionSentenceRepository.findById(requestDto.getQuestionId())
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 Sentence 문제 ID(PK) 입니다."));

        UUIDFile uuidFile = uuidFileService.saveSubmission(member, requestDto.getAnswerFile());

        GradeMLResponse gradeMLResponse = gradeMLServer(GradeMLRequest.Sentence.builder()
                .submissionAnswerFilePath(uuidFile.getUuid())
                .questionContent(questionSentence.getContent())
                .pronunciationFilePath(questionSentence.getPronunciationFilePath())
                .build());

        SolvingRecord solvingRecord = SolvingRecord.builder()
                .isCorrect(gradeMLResponse.getIsCorrect())
                .submissionAnswer(uuidFile.getFileUrl())
                .questionResponseType(requestDto.getQuestionResponseType())
                .member(member)
                .questionSentence(questionSentence)
                .build();

        solvingRecordRepository.save(solvingRecord);

        dateAchievementService.addSolvingRecord(solvingRecord);

        return SolvingRecordResponseDto.Create.builder()
                .questionSentenceId(questionSentence.getId())
                .isCorrect(gradeMLResponse.getIsCorrect())
                .questionResponseType(solvingRecord.getQuestionResponseType())
                .answer(questionSentence.getContent())
                .answerPronunciationPath(questionSentence.getPronunciationFilePath())
                .answerVideoFilePath(questionSentence.getVideoPath())
                .submissionAnswer(solvingRecord.getSubmissionAnswer())
                .build();
    }

    // TODO : ML 통신 구현
    private GradeMLResponse gradeMLServer(GradeMLRequest.Word gradeMLRequest) {
        GradeMLResponse gradeMLResponse = new GradeMLResponse();
        return gradeMLResponse;
    }

    // TODO : ML 통신 구현
    private GradeMLResponse gradeMLServer(GradeMLRequest.Sentence gradeMLRequest) {
        GradeMLResponse gradeMLResponse = new GradeMLResponse();
        return gradeMLResponse;
    }


}
