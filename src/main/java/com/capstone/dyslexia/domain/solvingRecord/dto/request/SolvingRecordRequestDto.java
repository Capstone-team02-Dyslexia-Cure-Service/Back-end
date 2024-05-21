package com.capstone.dyslexia.domain.solvingRecord.dto.request;

import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class SolvingRecordRequestDto {

    @NotBlank(message = "questionId는 공백이면 안 됩니다.")
    private Long questionId;

    @NotBlank(message = "question response type은 공백이면 안 됩니다.")
    private QuestionResponseType questionResponseType;

    private String answer;

    private MultipartFile answerMultipartFile;

}
