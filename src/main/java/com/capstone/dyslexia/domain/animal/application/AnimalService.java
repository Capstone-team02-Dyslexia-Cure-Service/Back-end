package com.capstone.dyslexia.domain.animal.application;

import com.capstone.dyslexia.domain.animal.dto.request.AnimalBuyRequestDto;
import com.capstone.dyslexia.domain.animal.dto.request.AnimalUpdateRequestDto;
import com.capstone.dyslexia.domain.animal.dto.response.AnimalResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {
    public AnimalResponseDto buyAnimal(Long memberId, AnimalBuyRequestDto animalType) {
        return null;
    }

    public AnimalResponseDto updateAnimal(Long memberId, Long animalId, AnimalUpdateRequestDto animalUpdateRequestDto) {
        return null;
    }

    public AnimalResponseDto findAllAnimal(Long memberId) {
        return null;
    }

    public AnimalResponseDto findAnimalById(Long memberId, Long animalId) {
        return null;
    }

    public AnimalResponseDto feedAnimal(Long memberId, Long animalId) {
        return null;
    }
}
