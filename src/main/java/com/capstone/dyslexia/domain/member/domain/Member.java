package com.capstone.dyslexia.domain.member.domain;

import com.capstone.dyslexia.domain.dateAchievement.domain.DateAchievement;
import com.capstone.dyslexia.domain.member.dto.request.MemberUpdateRequestDto;
import com.capstone.dyslexia.domain.solvingRecord.domain.SolvingRecord;
import com.capstone.dyslexia.domain.store.domain.Store;
import com.capstone.dyslexia.domain.test.domain.Test;
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

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "is_evaluated")
    private Boolean isEvaluated;

    @Column(name = "level")
    private Double level;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SolvingRecord> solvingRecordList;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Store store;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Test> testList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DateAchievement> dateAchievementList;

    public void updateMember(MemberUpdateRequestDto memberUpdateRequestDto) {
        this.email = memberUpdateRequestDto.getEmail();
        this.password = memberUpdateRequestDto.getPassword();
        this.age = memberUpdateRequestDto.getAge();
    }

    public Member updateMemberLevel(Double level) {
        this.isEvaluated = true;
        this.level = level;
        return this;
    }
}
