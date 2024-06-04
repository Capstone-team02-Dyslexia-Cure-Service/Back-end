package com.capstone.dyslexia.domain.animal.presentation;

import com.capstone.dyslexia.domain.animal.application.AnimalService;
import com.capstone.dyslexia.domain.animal.domain.AnimalType;
import com.capstone.dyslexia.domain.animal.dto.response.AnimalResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/animal")
@Tag(name = "Animal", description = "Animal API")
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping("/buy")
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalResponseDto createAnimal(
            @RequestHeader Long memberId,
            @RequestBody AnimalType animalType
            ) {
        return animalService.createAnimal(memberId, animalType);
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public AnimalResponseDto updateAnimal(
            @RequestHeader Long memberId,
            @RequestHeader Long animalId,
            @RequestBody String nickname
    ) {
        return animalService.updateAnimal(memberId, animalId, nickname);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalResponseDto> findAllAnimal(
            @RequestHeader Long memberId
    ) {
        return animalService.findAllAnimal(memberId);
    }

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public AnimalResponseDto findAnimalById(
            @RequestHeader Long memberId,
            @RequestHeader Long animalId
    ) {
        return animalService.findAnimalById(memberId, animalId);
    }

    @GetMapping("/feed")
    @ResponseStatus(HttpStatus.OK)
    public AnimalResponseDto feedAnimal(
            @RequestHeader Long memberId,
            @RequestHeader Long animalId
    ) {
        return animalService.feedAnimal(memberId, animalId);
    }

}
