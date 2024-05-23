package com.capstone.dyslexia.domain.solvingRecord.dto.ml;

import com.capstone.dyslexia.domain.question.domain.QuestionType;
import lombok.Builder;

@Builder
public class GradeMLRequest {

    @Builder
    public static class Word {
        private String submissionAnswerFilePath;

        private String questionContent;
    }

    @Builder
    public static class Sentence {
        private String submissionAnswerFilePath;

        private String questionContent;

        private String pronunciationFilePath;
    }

}
