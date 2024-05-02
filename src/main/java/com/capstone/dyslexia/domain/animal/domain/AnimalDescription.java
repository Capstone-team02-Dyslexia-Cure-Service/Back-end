package com.capstone.dyslexia.domain.animal.domain;

import lombok.Getter;

@Getter
public enum AnimalDescription {
    DOLPHIN_DESCRIPTION("돌고래 동물 설명"),
    SEAL_DESCRIPTION("물개 동물 설명"),
    PENGUIN_DESCRIPTION("펭귄 동물 설명"),
    MONKEY_DESCRIPTION("원숭이 동물 설명");

    private final String description;

    AnimalDescription(String description) {
        this.description = description;
    }
}
