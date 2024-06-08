package com.capstone.dyslexia.domain.animal.domain;

import lombok.Getter;

import static com.capstone.dyslexia.domain.animal.domain.AnimalDefaultNickName.*;
import static com.capstone.dyslexia.domain.animal.domain.AnimalDescription.*;
import static com.capstone.dyslexia.domain.animal.domain.AnimalHabitatType.*;

@Getter
public enum AnimalType {
    DOLPHIN(DOLPHIN_DEFAULT_NICKNAME, OCEAN, DOLPHIN_DESCRIPTION),
    SEAL(SEAL_DEFAULT_NICKNAME, OCEAN, SEAL_DESCRIPTION),
    PENGUIN(PENGUIN_DEFAULT_NICKNAME ,POLAR_REGIONS, PENGUIN_DESCRIPTION),
    MONKEY(MONKEY_DEFAULT_NICKNAME , JUNGLE, MONKEY_DESCRIPTION);

    private final AnimalDefaultNickName animalDefaultNickName;
    private final AnimalHabitatType animalHabitatType;
    private final AnimalDescription animalDescription;

    AnimalType(AnimalDefaultNickName animalDefaultNickName, AnimalHabitatType animalHabitatType, AnimalDescription animalDescription) {
        this.animalDefaultNickName = animalDefaultNickName;
        this.animalHabitatType = animalHabitatType;
        this.animalDescription = animalDescription;
    }
}
