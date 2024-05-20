package com.capstone.dyslexia.domain.level.application;

import com.capstone.dyslexia.domain.level.config.LevelRangeConfig;
import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LevelRangeService {

    private final LevelRangeConfig levelRangeConfig;

    public LevelRangeConfig.LevelRange getLevelRange(Double level) {
        List<LevelRangeConfig.LevelRange> levelRanges = levelRangeConfig.getLevelRangeList();
        for (LevelRangeConfig.LevelRange levelRange : levelRanges) {
            if (level > levelRange.getMin() && level < levelRange.getMax()) {
                return levelRange;
            }
        }
        return null;
    }

    public EnumMap<QuestionResponseType, Double> getQuestionResponseProbability(Double level) {
        LevelRangeConfig.LevelRange levelRange = getLevelRange(level);
        return levelRange != null ? levelRange.getProbabilities() : null;
    }

}
