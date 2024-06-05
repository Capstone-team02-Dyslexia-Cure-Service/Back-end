package com.capstone.dyslexia.domain.level.config;

import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "basic-test")
@Getter
@Setter
public class BasicTestLevelRangeConfig {

    private Map<QuestionResponseType, Double> probabilities;

    public EnumMap<QuestionResponseType, Double> getProbabilitiesAsEnumMap() {
        return new EnumMap<>(probabilities);
    }
}
