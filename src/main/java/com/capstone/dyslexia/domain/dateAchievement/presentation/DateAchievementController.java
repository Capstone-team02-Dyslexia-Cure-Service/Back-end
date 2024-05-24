package com.capstone.dyslexia.domain.dateAchievement.presentation;


import com.capstone.dyslexia.domain.dateAchievement.application.DateAchievementService;
import com.capstone.dyslexia.domain.dateAchievement.dto.request.DateAchievementPeriodRequestDto;
import com.capstone.dyslexia.domain.dateAchievement.dto.response.DateAchievementResponseDto;
import com.capstone.dyslexia.global.payload.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/achievement")
@Tag(name = "DateAchievement", description = "Date Achievement API")
public class DateAchievementController {

    private final DateAchievementService dateAchievementService;

    @GetMapping("/all")
    public ApiResponseTemplate<List<DateAchievementResponseDto>> findAllByMemberId(
            @RequestHeader Long memberId
    ) {
        return ApiResponseTemplate.ok(dateAchievementService.findAllByMemberId(memberId));
    }

    @GetMapping
    public ApiResponseTemplate<DateAchievementResponseDto> findByDate(
            @RequestHeader Long memberId,
            @RequestHeader Date date
    ) {
        return ApiResponseTemplate.ok(dateAchievementService.findByDate(memberId, date));
    }

    @PostMapping("/period")
    public ApiResponseTemplate<List<DateAchievementResponseDto>> findByPeriod(
            @RequestHeader Long memberId,
            @RequestBody DateAchievementPeriodRequestDto dateAchievementPeriodRequestDto
    ) {
        return ApiResponseTemplate.ok(dateAchievementService.findByPeriod(memberId, dateAchievementPeriodRequestDto));
    }

}
