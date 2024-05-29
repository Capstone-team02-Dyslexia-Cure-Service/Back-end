package com.capstone.dyslexia.domain.dateAchievement.presentation;


import com.capstone.dyslexia.domain.dateAchievement.application.DateAchievementService;
import com.capstone.dyslexia.domain.dateAchievement.dto.request.DateAchievementPeriodRequestDto;
import com.capstone.dyslexia.domain.dateAchievement.dto.response.DateAchievementResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/achievement")
@Tag(name = "DateAchievement", description = "Date Achievement API")
public class DateAchievementController {

    private final DateAchievementService dateAchievementService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<DateAchievementResponseDto.Find> findAllByMemberId(
            @RequestParam int pageNumber,
            @RequestParam int size,
            @RequestHeader Long memberId
    ) {
        PageRequest pageRequest = PageRequest.of(pageNumber, size, Sort.by("achievementDate").descending());
        return dateAchievementService.findAllByMemberId(memberId, pageRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public DateAchievementResponseDto.Find findByDate(
            @RequestHeader Long memberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return dateAchievementService.findByDate(memberId, date);
    }

    @PostMapping("/period")
    @ResponseStatus(HttpStatus.OK)
    public List<DateAchievementResponseDto.Find> findByPeriod(
            @RequestParam int pageNumber,
            @RequestParam int size,
            @RequestHeader Long memberId,
            @RequestBody DateAchievementPeriodRequestDto.FindByPeriod dateAchievementPeriodRequestDto
    ) {
        PageRequest pageRequest = PageRequest.of(pageNumber, size, Sort.by("achievementDate").descending());
        return dateAchievementService.findByPeriod(memberId, pageRequest, dateAchievementPeriodRequestDto);
    }

}
