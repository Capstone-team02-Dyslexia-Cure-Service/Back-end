package com.capstone.dyslexia.domain.solvingRecord.dto.response;

import lombok.Builder;

@Builder
public class SolvingRecordResponseDto {

    private Long id;

    private Boolean isCorrect;

    private Long questionId;

}
