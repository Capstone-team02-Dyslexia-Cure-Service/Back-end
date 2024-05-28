package com.capstone.dyslexia.domain.dateAchievement.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class DateAchievementPeriodRequestDto {

    @Getter
    @Builder
    public static class FindByPeriod {
        private LocalDate startDate;
        private LocalDate endDate;
    }

}
