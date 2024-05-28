package com.capstone.dyslexia.domain.dateAchievement.domain.repository;

import com.capstone.dyslexia.domain.dateAchievement.domain.DateAchievement;
import com.capstone.dyslexia.domain.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DateAchievementRepository extends JpaRepository<DateAchievement, Long> {

    Page<DateAchievement> findAllByMember(Member member, Pageable pageable);

    List<DateAchievement> findByMemberAndAchievementDate(Member member, LocalDate achievementDate);
    
    Page<DateAchievement> findByMemberAndAchievementDateBetween(Member member, LocalDate startDate, LocalDate endDate, Pageable pageable);

    Optional<DateAchievement> findByAchievementDate(LocalDate createdAtDate);
}
