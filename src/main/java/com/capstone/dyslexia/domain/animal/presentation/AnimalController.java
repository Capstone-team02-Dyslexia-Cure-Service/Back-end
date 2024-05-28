package com.capstone.dyslexia.domain.animal.presentation;

import com.capstone.dyslexia.domain.animal.application.AnimalService;
import com.capstone.dyslexia.domain.animal.domain.AnimalType;
import com.capstone.dyslexia.domain.animal.dto.response.AnimalResponseDto;
import com.capstone.dyslexia.global.payload.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/animal")
@Tag(name = "Animal", description = "Animal API")
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping("/buy")
    public ApiResponseTemplate<AnimalResponseDto> createAnimal(
            @RequestHeader Long memberId,
            @RequestBody AnimalType animalType
            ) {
        return ApiResponseTemplate.created(animalService.createAnimal(memberId, animalType));
    }

    @PostMapping("/update")
    public ApiResponseTemplate<AnimalResponseDto> updateAnimal(
            @RequestHeader Long memberId,
            @RequestHeader Long animalId,
            @RequestBody String nickname
    ) {
        return ApiResponseTemplate.ok(animalService.updateAnimal(memberId, animalId, nickname));
    }

    @GetMapping("/all")
    public ApiResponseTemplate<List<AnimalResponseDto>> findAllAnimal(
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
