package com.capstone.dyslexia.domain.animal.dto.response;

import com.capstone.dyslexia.domain.animal.domain.AnimalType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AnimalResponseDto {

    private Long id;

    private String animalType;

    private String habitatType;

    private String description;

    private String nickname;

    private LocalDateTime hungerTimer;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static AnimalResponseDto buildAnimal(Long id, AnimalType animalTypeEnum, String nickname, LocalDateTime hungerTimer, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return AnimalResponseDto.builder()
                .id(id)
                .animalType(animalTypeEnum.toString())
                .habitatType(animalTypeEnum.getAnimalHabitatType().toString())
                .description(animalTypeEnum.getAnimalDescription().getDescription())
                .nickname(nickname)
                .hungerTimer(hungerTimer)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
