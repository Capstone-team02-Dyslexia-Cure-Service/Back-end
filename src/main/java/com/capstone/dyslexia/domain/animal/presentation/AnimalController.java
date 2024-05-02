package com.capstone.dyslexia.domain.animal.presentation;

import com.capstone.dyslexia.domain.animal.application.AnimalService;
import com.capstone.dyslexia.domain.animal.dto.request.AnimalBuyRequestDto;
import com.capstone.dyslexia.domain.animal.dto.request.AnimalUpdateRequestDto;
import com.capstone.dyslexia.domain.animal.dto.response.AnimalResponseDto;
import com.capstone.dyslexia.global.payload.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/animal")
@Tag(name = "Animal", description = "Animal API")
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping("/buy")
    public ApiResponseTemplate<AnimalResponseDto> buyAnimal(
            @RequestHeader Long memberId,
            @Valid @RequestBody AnimalBuyRequestDto animalBuyRequestDto
            ) {
        return ApiResponseTemplate.created(animalService.buyAnimal(memberId, animalBuyRequestDto));
    }

    @PostMapping("/update")
    public ApiResponseTemplate<AnimalResponseDto> updateAnimal(
            @RequestHeader Long memberId,
            @RequestHeader Long animalId,
            @Valid @RequestBody AnimalUpdateRequestDto animalUpdateRequestDto
    ) {
        return ApiResponseTemplate.ok(animalService.updateAnimal(memberId, animalId, animalUpdateRequestDto));
    }

    @GetMapping("/all")
    public ApiResponseTemplate<AnimalResponseDto> findAllAnimal(
            @RequestHeader Long memberId
    ) {
        return ApiResponseTemplate.ok(animalService.findAllAnimal(memberId));
    }

    @GetMapping
    public ApiResponseTemplate<AnimalResponseDto> findAnimalById(
            @RequestHeader Long memberId,
            @RequestHeader Long animalId
    ) {
        return ApiResponseTemplate.ok(animalService.findAnimalById(memberId, animalId));
    }

    @GetMapping("/feed")
    public ApiResponseTemplate<AnimalResponseDto> feedAnimal(
            @RequestHeader Long memberId,
            @RequestHeader Long animalId
    ) {
        return ApiResponseTemplate.ok(animalService.feedAnimal(memberId, animalId));
    }

}
