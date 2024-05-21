package com.capstone.dyslexia.domain.solvingRecord.dto.response;

import lombok.Builder;

@Builder
public class SolvingRecordCreateResponseDto {

    private Long questionId;

    private Boolean isCorrect;

    private String answer;

}
