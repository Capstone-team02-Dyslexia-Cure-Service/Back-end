package com.capstone.dyslexia.domain.question.dto.request;

import lombok.Builder;
import lombok.Getter;

public class QuestionRequestDto {

    @Getter
    @Builder
    public static class CreateWord {
        private String content;
    }

    @Getter
    @Builder
    public static class CreateSentence {
        private String content;

        private String pronunciationFilePath;
        
        private String videoPath;
    }

}
