package com.capstone.dyslexia.domain.solvingRecord.dto.ml;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GradeMLRequest {

    @Getter
    @Builder
    public static class Word {
        private String submissionAnswerFilePath;

        private String questionContent;
    }

    @Getter
    @Builder
    public static class Sentence {
        private String submissionAnswerFilePath;

        private String questionContent;

        private String pronunciationFilePath;
    }

}
