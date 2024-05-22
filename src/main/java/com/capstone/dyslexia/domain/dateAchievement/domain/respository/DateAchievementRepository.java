package com.capstone.dyslexia.domain.dateAchievement.domain.respository;

import com.capstone.dyslexia.domain.dateAchievement.domain.DateAchievement;
import com.capstone.dyslexia.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DateAchievementRepository extends JpaRepository<DateAchievement, Long> {
    List<DateAchievement> findAllByMember(Member member);
}
