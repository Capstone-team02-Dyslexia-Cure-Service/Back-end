package com.capstone.dyslexia.domain.solvingRecord.dto.ml;

import lombok.Getter;

public class GradeMLResponse {

    @Getter
    public static class Word {
        private String accuracyFeedback;

        private Boolean isCorrect;
    }

    @Getter
    public static class ReadSentence {
        private String accuracyFeedback;

        private Boolean isCorrect;

        private String speedFeedback;
    }

}
