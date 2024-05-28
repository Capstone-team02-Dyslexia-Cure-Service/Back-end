package com.capstone.dyslexia.domain.solvingRecord.dto.request;

import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class SolvingRecordRequestDto {

    @Getter
    @Data
    public static class Create {
        @Positive(message = "question id는 0보다 커야 합니다.")
        private Long questionId;

        @NotBlank(message = "question response type은 공백이면 안 됩니다.")
        private String questionResponseType;

        private String answer;
    }

    @Getter
    public static class Convert {
        private Long questionId;

        private QuestionResponseType questionResponseType;

        private String answer;

        private MultipartFile answerFile;

        public Convert(Create create, MultipartFile answerFile) {
            this.questionId = create.getQuestionId();
            this.questionResponseType = QuestionResponseType.valueOf(create.getQuestionResponseType());
            this.answer = create.getAnswer();
            this.answerFile = answerFile;
        }
    }

    @Getter
    public static class CreateString {
        private Long questionId;

        private QuestionResponseType questionResponseType;

        private String answer;

        public CreateString(Convert convertWithFile) {
            this.questionId = convertWithFile.getQuestionId();
            this.questionResponseType = convertWithFile.getQuestionResponseType();
            this.answer = convertWithFile.getAnswer();
        }
    }

    @Getter
    public static class CreateFile {
        private Long questionId;

        private QuestionResponseType questionResponseType;

        private MultipartFile answerFile;

        public CreateFile(Convert convertWithFile) {
            this.questionId = convertWithFile.getQuestionId();
            this.questionResponseType = convertWithFile.getQuestionResponseType();
            this.answerFile = convertWithFile.getAnswerFile();
        }
    }


}
