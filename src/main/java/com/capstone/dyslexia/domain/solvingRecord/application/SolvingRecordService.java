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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.capstone.dyslexia.domain.question.domain.QuestionResponseType.*;
import static com.capstone.dyslexia.global.error.ErrorCode.*;

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

    private final GradeMLService gradeMLService;


    public SolvingRecordResponseDto.Response findSolvingRecordById(Long memberId, Long solvingRecordId) {
        memberService.memberValidation(memberId);

        SolvingRecord solvingRecord = solvingRecordRepository.findById(solvingRecordId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "해당 문제 풀이 기록이 존재하지 않습니다."));

        return SolvingRecordResponseDto.Response.from(solvingRecord);
    }

    @Transactional
    public SolvingRecordResponseDto.Response solveOneQuestion(Long memberId, SolvingRecordRequestDto.CreateMerged solvingRecordRequestDto) {
        SolvingRecord solvingRecord = createSolvingRecord(memberId, solvingRecordRequestDto);
        solvingRecordRepository.save(solvingRecord);

        List<SolvingRecord> solvingRecordList = new ArrayList<>();

        solvingRecordList.add(solvingRecord);

        dateAchievementService.addSolvingRecord(solvingRecordList);

        return SolvingRecordResponseDto.Response.from(solvingRecord);
    }

    @Transactional
    public SolvingRecord createSolvingRecord(Long memberId, SolvingRecordRequestDto.CreateMerged solvingRecordRequest) {
        Member member = memberService.memberValidation(memberId);

        SolvingRecord solvingRecord;

        // SELECT_WORD, WRITE_WORD
        if (solvingRecordRequest.getQuestionResponseType().equals(SELECT_WORD) || solvingRecordRequest.getQuestionResponseType().equals(WRITE_WORD)) {
            QuestionWord questionWord = questionWordRepository.findById(solvingRecordRequest.getQuestionId())
                    .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 Word 문제 ID(PK) 입니다."));

            GradeMLResponse.Word gradeMLResponse = gradeMLService.gradeWriteWordML(GradeMLRequest.Write.builder()
                    .memberSubmissionString(solvingRecordRequest.getAnswer())
                    .questionContent(questionWord.getContent())
                    .build());

            solvingRecord = SolvingRecord.builder()
                    .isCorrect(gradeMLResponse.getIsCorrect())
                    .submissionAnswer(solvingRecordRequest.getAnswer())
                    .questionResponseType(solvingRecordRequest.getQuestionResponseType())
                    .member(member)
                    .questionWord(questionWord)
                    .accuracyFeedback(gradeMLResponse.getAccuracyFeedback())
                    .build();
        } else {    // READ_WORD, READ_SENTENCE
            if (solvingRecordRequest.getQuestionResponseType().equals(READ_WORD)) {
                QuestionWord questionWord = questionWordRepository.findById(solvingRecordRequest.getQuestionId())
                        .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 Word 문제 ID(PK) 입니다."));

                UUIDFile uuidFile = uuidFileService.saveSubmission(member, solvingRecordRequest.getAnswerFile());

                GradeMLResponse.Word gradeMLResponse = gradeMLService.gradeReadWordML(GradeMLRequest.ReadWord.builder()
                        .memberSubmissionAnswerFilePath(uuidFile.getFileUrl())
                        .questionContent(questionWord.getContent())
                        .build());
                solvingRecord = SolvingRecord.builder()
                        .isCorrect(gradeMLResponse.getIsCorrect())
                        .submissionAnswer(uuidFile.getFileUrl())
                        .questionResponseType(solvingRecordRequest.getQuestionResponseType())
                        .member(member)
                        .questionWord(questionWord)
                        .accuracyFeedback(gradeMLResponse.getAccuracyFeedback())
                        .build();
            } else {
                QuestionSentence questionSentence = questionSentenceRepository.findById(solvingRecordRequest.getQuestionId())
                        .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "존재하지 않는 Sentence 문제 ID(PK) 입니다."));

                UUIDFile uuidFile = uuidFileService.saveSubmission(member, solvingRecordRequest.getAnswerFile());

                GradeMLResponse.ReadSentence gradeMLResponse = gradeMLService.gradeReadSentenceML(GradeMLRequest.ReadSentence.builder()
                        .memberSubmissionAnswerFilePath(uuidFile.getFileUrl())
                        .questionContent(questionSentence.getContent())
                        .questionPronunciationFilePath(questionSentence.getPronunciationFilePath())
                        .build());
                solvingRecord = SolvingRecord.builder()
                        .isCorrect(gradeMLResponse.getIsCorrect())
                        .submissionAnswer(uuidFile.getFileUrl())
                        .questionResponseType(solvingRecordRequest.getQuestionResponseType())
                        .member(member)
                        .questionSentence(questionSentence)
                        .accuracyFeedback(gradeMLResponse.getAccuracyFeedback())
                        .speedFeedback(gradeMLResponse.getSpeedFeedback())
                        .build();
            }
        }

        return solvingRecord;
    }

}

