package com.capstone.dyslexia.domain.dateAchievement.application;

import com.capstone.dyslexia.domain.dateAchievement.domain.DateAchievement;
import com.capstone.dyslexia.domain.dateAchievement.domain.respository.DateAchievementRepository;
import com.capstone.dyslexia.domain.dateAchievement.dto.request.DateAchievementPeriodRequestDto;
import com.capstone.dyslexia.domain.dateAchievement.dto.response.DateAchievementResponseDto;
import com.capstone.dyslexia.domain.member.application.MemberService;
import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.solvingRecord.domain.SolvingRecord;
import com.capstone.dyslexia.domain.solvingRecord.domain.respository.SolvingRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//import static jdk.internal.foreign.abi.aarch64.AArch64Architecture.r1;
//import static jdk.internal.foreign.abi.aarch64.AArch64Architecture.r2;

@Service
@RequiredArgsConstructor
@Transactional
public class DateAchievementService {

    private final DateAchievementRepository dateAchievementRepository;
    private final SolvingRecordRepository solvingRecordRepository;

    private final MemberService memberService;

    public List<DateAchievementResponseDto> findAllByMemberId(Long memberId) {
        return null;
    }

    public DateAchievementResponseDto findByDate(Long memberId, Date date) {
        return null;
    }

    public List<DateAchievementResponseDto> findByPeriod(Long memberId, DateAchievementPeriodRequestDto dateAchievementPeriodRequestDto) {
        return null;
    }

    public DateAchievementResponseDto updateDateAchievement(Long memberId) {
        Member member = memberService.memberValidation(memberId);
        List<SolvingRecord> solvingRecordList = solvingRecordRepository.findSolvingRecordsByMember(member);

        solvingRecordList.sort((r1, r2) -> r1.getCreatedAt().compareTo(r2.getCreatedAt()));

        List<LocalDate> existingDates = dateAchievementRepository.findAllByMember(member)
                .stream()
                .map(DateAchievement::getAchievementDate)
                .toList();


        /*
        solvingRecordList.stream()
                .map(SolvingRecord::getCreatedAt)
                .distinct()
                .filter(date -> !existingDates.contains(date))
                .forEach(date -> {
                    DateAchievement dateAchievement = DateAchievement.builder()
                            .member(member)
                            .solvingRecordList()
                            .build();
                });

         */
        return null;
    }


}
