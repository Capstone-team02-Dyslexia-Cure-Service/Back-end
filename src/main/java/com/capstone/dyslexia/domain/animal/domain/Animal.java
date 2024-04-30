package com.capstone.dyslexia.domain.animal.domain;

import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.joda.time.DateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "animal")
public class Animal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_type")
    private AnimalType animalType;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "description")
    private String description;

    @Column(name = "hunger_timer")
    private DateTime hungerTimer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
