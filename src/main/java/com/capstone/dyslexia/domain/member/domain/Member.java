package com.capstone.dyslexia.domain.member.domain;

import com.capstone.dyslexia.domain.animal.domain.Animal;
import com.capstone.dyslexia.domain.solvingRecord.domain.SolvingRecord;
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
    @JoinColumn(name = "animal_list")
    private List<Animal> animalList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JoinColumn(name = "solving_record_list")
    private List<SolvingRecord> solvingRecordList;
}
