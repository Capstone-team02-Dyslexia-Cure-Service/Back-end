package com.capstone.dyslexia.domain.question.dto.request;

import com.capstone.dyslexia.domain.question.domain.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

public class QuestionRequestDto {

    @Getter
    public static class Create {
        @NotNull(message = "Question Type은 필수입니다.")
        private QuestionType questionType;

        @NotBlank(message = "Question 내용은 비어서는 안 됩니다.")
        private String content;

        private String pronunciationFilePath;

        private String videoPath;
    }

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
