package com.capstone.dyslexia.domain.animal.domain;

import lombok.Getter;

import static com.capstone.dyslexia.domain.animal.domain.AnimalDescription.*;
import static com.capstone.dyslexia.domain.animal.domain.AnimalHabitatType.*;

@Getter
public enum AnimalType {
    DOLPHIN(OCEAN, DOLPHIN_DESCRIPTION),
    SEAL(OCEAN, SEAL_DESCRIPTION),
    PENGUIN(POLAR_REGIONS, PENGUIN_DESCRIPTION),
    MONKEY(JUNGLE, MONKEY_DESCRIPTION);

    private final AnimalHabitatType animalHabitatType;
    private final AnimalDescription animalDescription;

    AnimalType(AnimalHabitatType animalHabitatType, AnimalDescription animalDescription) {
        this.animalHabitatType = animalHabitatType;
        this.animalDescription = animalDescription;
    }
}
