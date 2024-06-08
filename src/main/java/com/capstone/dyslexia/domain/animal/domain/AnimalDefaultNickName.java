package com.capstone.dyslexia.domain.animal.domain;

import com.capstone.dyslexia.global.util.StaticValue;
import lombok.Getter;

@Getter
public enum AnimalDefaultNickName {
    DOLPHIN_DEFAULT_NICKNAME(StaticValue.dolphinDefaultNickName),
    SEAL_DEFAULT_NICKNAME(StaticValue.sealDefaultNickName),
    PENGUIN_DEFAULT_NICKNAME(StaticValue.penguinDefaultNickName),
    MONKEY_DEFAULT_NICKNAME(StaticValue.monkeyDefaultNickName);

    private final String defaultNickName;

    AnimalDefaultNickName(String defaultNickName) {
        this.defaultNickName = defaultNickName;
    }

}
