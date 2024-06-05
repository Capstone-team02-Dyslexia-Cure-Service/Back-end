package com.capstone.dyslexia.domain.animal.domain;

import com.capstone.dyslexia.global.util.StaticValue;
import lombok.Getter;

@Getter
public enum AnimalDescription {
    DOLPHIN_DESCRIPTION(StaticValue.dolphinDescription),
    SEAL_DESCRIPTION(StaticValue.sealDescription),
    PENGUIN_DESCRIPTION(StaticValue.penguinDescription),
    MONKEY_DESCRIPTION(StaticValue.monkeyDescription);

    private final String description;

    AnimalDescription(String description) {
        this.description = description;
    }
}
