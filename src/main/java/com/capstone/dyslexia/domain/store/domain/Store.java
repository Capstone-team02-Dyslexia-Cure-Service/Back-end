package com.capstone.dyslexia.domain.store.domain;

import com.capstone.dyslexia.domain.animal.domain.Animal;
import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "store")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    private Member member;


    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Animal> havingAnimalList;

    public Store setMember(Member member) {
        this.member = member;
        return this;
    }

}
