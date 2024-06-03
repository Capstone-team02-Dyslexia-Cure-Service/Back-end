package com.capstone.dyslexia.domain.solvingRecord.dto.ml;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

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
