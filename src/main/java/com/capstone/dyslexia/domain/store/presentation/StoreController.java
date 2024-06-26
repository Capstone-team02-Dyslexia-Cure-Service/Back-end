package com.capstone.dyslexia.domain.store.presentation;

import com.capstone.dyslexia.domain.store.application.StoreService;
import com.capstone.dyslexia.domain.store.dto.request.StoreResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
@Tag(name = "Store", description = "Store API")
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StoreResponseDto> findAllOwnedAnimal(
            @RequestHeader Long memberId
    ) {
        return ResponseEntity.ok(storeService.findAllOwnedAnimal(memberId));
    }

}
