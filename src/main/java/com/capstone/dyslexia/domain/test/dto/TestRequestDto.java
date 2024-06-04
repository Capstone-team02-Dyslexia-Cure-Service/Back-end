package com.capstone.dyslexia.domain.test.dto;

import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TestRequestDto {

    @Builder
    @Getter
    public static class AnswerBody {
        private Long testId;

        private Long questionId;

        private String answer;

        private QuestionResponseType questionResponseType;
    }
}
