package com.capstone.dyslexia.domain.question.dto.request;

import com.capstone.dyslexia.domain.question.domain.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateQuestionRequestDto {

    @NotNull(message = "Question Type은 필수입니다.")
    private QuestionType questionType;

    @NotBlank(message = "Question 내용은 비어서는 안 됩니다.")
    private String content;

    @NotBlank(message = "발음 예시 파일은 꼭 필요합니다.")
    private String pronunciationFilePath;

    @NotBlank(message = "발음 예시 영상은 꼭 필요합니다.")
    private String videoPath;

}
