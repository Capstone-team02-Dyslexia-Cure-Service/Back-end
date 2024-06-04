package com.capstone.dyslexia.domain.question.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class QuestionRequestDto {

    @Getter
    @Builder
    public static class CreateWord {
        private String content;

        private MultipartFile videoFile;
    }

    @Getter
    @Builder
    public static class CreateSentence {
        private String content;

        private MultipartFile pronunciationFile;
        
        private MultipartFile videoFile;
    }

}
