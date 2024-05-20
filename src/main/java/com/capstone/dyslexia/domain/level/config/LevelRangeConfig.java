package com.capstone.dyslexia.domain.level.config;

import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "level-rages")
@Getter
public class LevelRangeConfig {

    private List<LevelRange> levelRangeList;

    @Getter
    public static class LevelRange {
        private Double min;
        private Double max;
        private EnumMap<QuestionResponseType, Double> probabilities;
    }

}
