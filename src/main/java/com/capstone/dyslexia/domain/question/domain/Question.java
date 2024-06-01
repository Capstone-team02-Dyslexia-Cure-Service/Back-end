package com.capstone.dyslexia.domain.question.domain;

import com.capstone.dyslexia.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@MappedSuperclass
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
