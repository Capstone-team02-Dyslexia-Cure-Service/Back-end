package com.capstone.dyslexia.domain.level.domain;

import com.capstone.dyslexia.domain.question.dto.QuestionResponseType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public enum LevelRange {
    L_LESS_THAN_3(null, 3.0, new ArrayList<EnumMap<QuestionResponseType, Double>>()),

    L_3_TO_9(new double[]{0.1, 0.6, 0.3, 0.0}),
    L_9_TO_27(new double[]{0.05, 0.15, 0.5, 0.3}),
    L_27_AND_ABOVE(new double[]{0.0, 0.05, 0.15, 0.8});

    private Double minLevel;
    private Double maxLevel;

    private List<EnumMap<QuestionResponseType, Double>> questionResponseTypeProbabilityEnumMap;

    LevelRange(Double minLevel, Double maxLevel, List<EnumMap<QuestionResponseType, Double>> questionResponseTypeProbabilityEnumMap) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.questionResponseTypeProbabilityEnumMap = questionResponseTypeProbabilityEnumMap;
    }

    double[] probabilities;

    LevelRange(double[] doubles) {}
}
