package com.capstone.dyslexia.domain.test.dto;

import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.question.dto.response.QuestionResponseDto;
import com.capstone.dyslexia.domain.test.domain.Test;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TestResponseDto {

    @Getter
    @Builder
    public static class Create {

        private Long testId;

        private Long memberId;

        private List<QuestionResponseDto.GetRandom> questionList;

        public static Create from(Test test, List<QuestionResponseDto.GetRandom> questionList) {
            return Create.builder()
                    .testId(test.getId())
                    .memberId(test.getMember().getId())
                    .questionList(questionList)
                    .build();
        }

    }


}
