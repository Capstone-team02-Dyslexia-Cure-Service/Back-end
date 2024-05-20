package com.capstone.dyslexia.domain.member.domain;

import com.capstone.dyslexia.domain.dateAchievement.domain.DateAchievement;
import com.capstone.dyslexia.domain.level.domain.Level;
import com.capstone.dyslexia.domain.member.dto.request.MemberUpdateRequestDto;
import com.capstone.dyslexia.domain.solvingRecord.domain.SolvingRecord;
import com.capstone.dyslexia.domain.store.domain.Store;
import com.capstone.dyslexia.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private Integer age;

    @Column(name = "level")
    private Double level;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<SolvingRecord> solvingRecordList;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Store store;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<DateAchievement> dateAchievementList;

    public void updateMember(MemberUpdateRequestDto memberUpdateRequestDto) {
        this.email = memberUpdateRequestDto.getEmail();
        this.password = memberUpdateRequestDto.getPassword();
        this.age = memberUpdateRequestDto.getAge();
    }

    public void updateMemberLevel(Double level) {
        this.level = level;
    }

    public void addSolvingRecordList(SolvingRecord solvingRecord) {
        this.solvingRecordList.add(solvingRecord);
    }

    public void addDateAchievementList(DateAchievement dateAchievement) {
        this.dateAchievementList.add(dateAchievement);
    }
}
