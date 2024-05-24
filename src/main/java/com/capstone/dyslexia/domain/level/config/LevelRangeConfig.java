package com.capstone.dyslexia.domain.level.config;

import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "level-ranges")
@Getter
public class LevelRangeConfig {

    private List<LevelRange> levelRangeList;

    @Getter
    public static class LevelRange {
        private Double min;
        private Double max;
        private Map<QuestionResponseType, Double> probabilities;

        public EnumMap<QuestionResponseType, Double> getProbabilitiesAsEnumMap() {
            return new EnumMap<>(probabilities);
        }
    }
}
