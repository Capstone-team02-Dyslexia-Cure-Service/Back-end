package com.capstone.dyslexia.domain.store.presentation;

import com.capstone.dyslexia.domain.store.application.StoreService;
import com.capstone.dyslexia.domain.store.dto.request.StoreResponseDto;
import com.capstone.dyslexia.global.payload.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
@Tag(name = "Store", description = "Store API")
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ApiResponseTemplate<StoreResponseDto> findAllOwnedAnimal(
            @RequestHeader Long memberId
    ) {
        return ApiResponseTemplate.ok(storeService.findAllOwnedAnimal(memberId));
    }

}
