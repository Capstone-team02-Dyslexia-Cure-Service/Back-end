package com.capstone.dyslexia.domain.dateAchievement.domain;

import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.solvingRecord.domain.SolvingRecord;
import com.capstone.dyslexia.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "DateAchievement")
public class DateAchievement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "date_achievement", cascade = CascadeType.ALL)
    @JoinColumn(name = "solving_record_list")
    private List<SolvingRecord> solvingRecordList;

    @Column(name = "achievement_date")
    private Date achievementDate;

    @Column(name = "correct_rate")
    private Double correctRate;

}
