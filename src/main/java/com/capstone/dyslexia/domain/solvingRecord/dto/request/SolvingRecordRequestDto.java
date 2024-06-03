package com.capstone.dyslexia.domain.solvingRecord.dto.request;

import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class SolvingRecordRequestDto {

    @Getter
    @Builder
    public static class CreateWrite {
        @Positive(message = "question id는 0보다 커야 합니다.")
        private Long questionId;

        @NotBlank(message = "question response type은 공백이면 안 됩니다.")
        private QuestionResponseType questionResponseType;

        private String answer;
    }

    @Getter
    @Builder
    public static class CreateRead {
        @Positive(message = "question id는 0보다 커야 합니다.")
        private Long questionId;

        @NotBlank(message = "question response type은 공백이면 안 됩니다.")
        private QuestionResponseType questionResponseType;
    }

    @Getter
    @Builder
    public static class CreateMerged {
        @Positive(message = "question id는 0보다 커야 합니다.")
        private Long questionId;

        @NotBlank(message = "question response type은 공백이면 안 됩니다.")
        private QuestionResponseType questionResponseType;

        private String answer;

        private MultipartFile answerFile;
    }

    @Getter
    @Builder
    public static class AnswerBody {
        private Long questionId;

        private String answer;

        private QuestionResponseType questionResponseType;
    }

}
