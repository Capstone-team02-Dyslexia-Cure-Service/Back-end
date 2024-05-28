package com.capstone.dyslexia.domain.dateAchievement.presentation;


import com.capstone.dyslexia.domain.dateAchievement.application.DateAchievementService;
import com.capstone.dyslexia.domain.dateAchievement.dto.request.DateAchievementPeriodRequestDto;
import com.capstone.dyslexia.domain.dateAchievement.dto.response.DateAchievementResponseDto;
import com.capstone.dyslexia.global.payload.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/achievement")
@Tag(name = "DateAchievement", description = "Date Achievement API")
public class DateAchievementController {

    private final DateAchievementService dateAchievementService;

    @GetMapping("/all")
    public ApiResponseTemplate<List<DateAchievementResponseDto.Find>> findAllByMemberId(
            @RequestHeader Long memberId,
            @RequestHeader Pageable pageable
    ) {
        return ApiResponseTemplate.ok(dateAchievementService.findAllByMemberId(memberId, pageable));
    }

    @GetMapping
    public ApiResponseTemplate<DateAchievementResponseDto.Find> findByDate(
            @RequestHeader Long memberId,
            @RequestHeader LocalDate date
    ) {
        return ApiResponseTemplate.ok(dateAchievementService.findByDate(memberId, date));
    }

    @PostMapping("/period")
    public ApiResponseTemplate<List<DateAchievementResponseDto.Find>> findByPeriod(
            @RequestHeader Long memberId,
            @RequestHeader Pageable pageable,
            @RequestBody DateAchievementPeriodRequestDto.FindByPeriod dateAchievementPeriodRequestDto
    ) {
        return ApiResponseTemplate.ok(dateAchievementService.findByPeriod(memberId, pageable, dateAchievementPeriodRequestDto));
    }

}
