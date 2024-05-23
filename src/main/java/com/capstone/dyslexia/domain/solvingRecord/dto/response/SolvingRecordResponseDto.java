package com.capstone.dyslexia.domain.solvingRecord.dto.response;

import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.question.domain.sentence.QuestionSentence;
import com.capstone.dyslexia.domain.question.domain.word.QuestionWord;
import com.capstone.dyslexia.domain.solvingRecord.domain.SolvingRecord;
import lombok.Builder;

public class SolvingRecordResponseDto {

    public static class Find {
        private Long id;

        private Boolean isCorrect;

        private QuestionResponseType questionResponseType;

        private Long questionId;

        private String submissionAnswer;

        public Find(SolvingRecord solvingRecord, QuestionWord questionWord) {
            this.id = solvingRecord.getId();
            this.isCorrect = solvingRecord.getIsCorrect();
            this.questionResponseType = solvingRecord.getQuestionResponseType();
            this.questionId = questionWord.getId();
            this.submissionAnswer = solvingRecord.getSubmissionAnswer();
        }

        public Find(SolvingRecord solvingRecord, QuestionSentence questionSentence) {
            this.id = solvingRecord.getId();
            this.isCorrect = solvingRecord.getIsCorrect();
            this.questionResponseType = solvingRecord.getQuestionResponseType();
            this.questionId = questionSentence.getId();
            this.submissionAnswer = solvingRecord.getSubmissionAnswer();
        }
    }

    @Builder
    public static class Create {
        private Long questionWordId;

        private Long questionSentenceId;

        private Boolean isCorrect;

        private QuestionResponseType questionResponseType;

        private String answer;

        private String answerPronunciationPath;

        private String answerVideoFilePath;

        private String submissionAnswer;
    }

}
