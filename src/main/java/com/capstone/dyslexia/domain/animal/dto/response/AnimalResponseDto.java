package com.capstone.dyslexia.domain.animal.dto.response;

import com.capstone.dyslexia.domain.animal.domain.Animal;
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

    public static AnimalResponseDto from(Animal animal) {
        return AnimalResponseDto.builder()
                .id(animal.getId())
                .animalType(animal.getAnimalType().toString())
                .habitatType(animal.getAnimalType().getAnimalHabitatType().toString())
                .description(animal.getAnimalType().getAnimalDescription().getDescription())
                .nickname(animal.getNickname())
                .hungerTimer(animal.getHungerTimer())
                .createdAt(animal.getCreatedAt())
                .updatedAt(animal.getUpdatedAt())
                .build();
    }
}
