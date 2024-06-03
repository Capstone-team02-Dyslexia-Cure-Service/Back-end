package com.capstone.dyslexia.domain.solvingRecord.dto.ml;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GradeMLRequest {

    @Getter
    @Builder
    public static class Write {
        private String memberSubmissionString;

        private String questionContent;
    }

    @Getter
    @Builder
    public static class ReadWord {
        private String memberSubmissionAnswerFilePath;

        private String questionContent;
    }

    @Getter
    @Builder
    public static class ReadSentence {
        private String memberSubmissionAnswerFilePath;

        private String questionContent;

        private String questionPronunciationFilePath;
    }

}
