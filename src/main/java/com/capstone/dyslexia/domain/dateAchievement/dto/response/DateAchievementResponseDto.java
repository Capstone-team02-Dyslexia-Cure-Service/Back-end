package com.capstone.dyslexia.domain.dateAchievement.dto.response;

import com.capstone.dyslexia.domain.dateAchievement.domain.DateAchievement;
import com.capstone.dyslexia.domain.solvingRecord.domain.SolvingRecord;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class DateAchievementResponseDto {

    @Getter
    @Builder
    public static class Find {
        private Long id;
        private Long memberId;
        private List<Long> solvingRecordIdList;
        private Double score;
        private LocalDate achievementDate;

        public static Find from(DateAchievement dateAchievement) {
            return Find.builder()
                    .id(dateAchievement.getId())
                    .memberId(dateAchievement.getMember().getId())
                    .solvingRecordIdList(dateAchievement.getSolvingRecordList().stream().map(SolvingRecord::getId).toList())
                    .score(dateAchievement.getScore())
                    .achievementDate(dateAchievement.getAchievementDate())
                    .build();
        }
    }
}
