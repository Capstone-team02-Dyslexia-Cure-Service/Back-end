package com.capstone.dyslexia.domain.solvingRecord.dto.response;

import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.question.domain.sentence.QuestionSentence;
import com.capstone.dyslexia.domain.question.domain.word.QuestionWord;
import com.capstone.dyslexia.domain.solvingRecord.domain.SolvingRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class SolvingRecordResponseDto {

    @Getter
    @Builder
    public static class Response {
        private Long id;

        private Long questionWordId;

        private Long questionSentenceId;

        private Boolean isCorrect;

        private QuestionResponseType questionResponseType;

        private String submissionAnswer;

        private String submissionFilePath;

        private String accuracyFeedback;

        private String speedFeedback;

        private String answer;

        private String answerPronunciationPath;

        private String answerVideoFilePath;

        public static Response from(SolvingRecord solvingRecord) {
            if (solvingRecord.getQuestionResponseType().equals(QuestionResponseType.READ_SENTENCE)) {
                return Response.builder()
                        .id(solvingRecord.getId())
                        .questionSentenceId(solvingRecord.getQuestionSentence().getId())
                        .isCorrect(solvingRecord.getIsCorrect())
                        .questionResponseType(solvingRecord.getQuestionResponseType())
                        .submissionAnswer(null)
                        .submissionFilePath(solvingRecord.getSubmissionFilePath())
                        .accuracyFeedback(solvingRecord.getAccuracyFeedback())
                        .speedFeedback(solvingRecord.getSpeedFeedback())
                        .answer(solvingRecord.getQuestionSentence().getContent())
                        .answerPronunciationPath(solvingRecord.getQuestionSentence().getPronunciationFilePath())
                        .answerVideoFilePath(solvingRecord.getQuestionSentence().getVideoPath())
                        .build();
            } else {
                return Response.builder()
                        .id(solvingRecord.getId())
                        .questionWordId(solvingRecord.getQuestionWord().getId())
                        .isCorrect(solvingRecord.getIsCorrect())
                        .questionResponseType(solvingRecord.getQuestionResponseType())
                        .submissionAnswer(solvingRecord.getSubmissionAnswer())
                        .submissionFilePath(null)
                        .accuracyFeedback(solvingRecord.getAccuracyFeedback())
                        .speedFeedback(null)
                        .answer(solvingRecord.getQuestionWord().getContent())
                        .answerPronunciationPath(null)
                        .answerVideoFilePath(solvingRecord.getQuestionWord().getVideoPath())
                        .build();
            }
        }
    }

}
