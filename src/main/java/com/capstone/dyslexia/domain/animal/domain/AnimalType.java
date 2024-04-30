package com.capstone.dyslexia.domain.animal.domain;

import lombok.Getter;

import static com.capstone.dyslexia.domain.animal.domain.AnimalHabitatType.*;

@Getter
public enum AnimalType {
    DOLPHIN(OCEAN),
    SEAL(OCEAN),
    PENGUIN(POLAR_REGIONS),
    MONKEY(JUNGLE);

    private final AnimalHabitatType animalHabitatType;

    AnimalType(AnimalHabitatType animalHabitatType) {
        this.animalHabitatType = animalHabitatType;
    }
}
