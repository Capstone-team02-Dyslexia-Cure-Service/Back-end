package com.capstone.dyslexia.domain.animal.domain;

import com.capstone.dyslexia.domain.store.domain.Store;
import com.capstone.dyslexia.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(name = "hunger_timer")
    private LocalDateTime hungerTimer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setHungerTimer(LocalDateTime hungerTimer) {
        this.hungerTimer = hungerTimer;
    }

}
