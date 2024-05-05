package com.capstone.dyslexia.domain.store.dto.request;

import com.capstone.dyslexia.domain.animal.dto.response.AnimalResponseDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class StoreResponseDto {

    private Long storeId;

    private Long memberId;

    private List<AnimalResponseDto> animalResponseDtoList;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
