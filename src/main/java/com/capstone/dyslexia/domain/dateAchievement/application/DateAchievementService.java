package com.capstone.dyslexia.domain.dateAchievement.application;

import com.capstone.dyslexia.domain.dateAchievement.domain.DateAchievement;
import com.capstone.dyslexia.domain.dateAchievement.dto.request.DateAchievementPeriodRequestDto;
import com.capstone.dyslexia.domain.dateAchievement.dto.response.DateAchievementResponseDto;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DateAchievementService {
    public List<DateAchievementResponseDto> findAllByMemberId(Long memberId) {
        return null;
    }

    public DateAchievementResponseDto findByDate(Long memberId, Date date) {
        return null;
    }

    public List<DateAchievementResponseDto> findByPeriod(Long memberId, DateAchievementPeriodRequestDto dateAchievementPeriodRequestDto) {
        return null;
    }

    public DateAchievementResponseDto updateDateAchievement(Long memberId, Date date) {
        return null;
    }
}
