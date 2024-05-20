package com.capstone.dyslexia.domain.question.application;

import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.question.dto.QuestionResponseType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import static com.capstone.dyslexia.domain.question.dto.QuestionResponseType.*;

public class ProbabilityCalculator {

    /*
    // 레벨 구간 정의

    // 주어진 레벨에 따라 확률을 계산하여 EnumMap 반환
    public static EnumMap<QuestionResponseType, Double> calculateProbabilities(int level) {
        LevelRange range;
        if (level < 3) {
            range = LevelRange.L_LESS_THAN_3;
        } else if (level < 9) {
            range = LevelRange.L_3_TO_9;
        } else if (level < 27) {
            range = LevelRange.L_9_TO_27;
        } else {
            range = LevelRange.L_27_AND_ABOVE;
        }

        EnumMap<QuestionResponseType, Double> result = new EnumMap<>(QuestionResponseType.class);
        double[] probabilities = range.probabilities;
        result.put(SELECT_WORD, probabilities[0]);
        result.put(WRITE_WORD, probabilities[1]);
        result.put(READ_WORD, probabilities[2]);
        result.put(READ_SENTENCE, probabilities[3]);
        return result;
    }

    public static List<Long> getQuestionTypeList(Long level, Long numberOfQuestions) {
        LevelRange range;
        if (level < 3) {
            range = LevelRange.L_LESS_THAN_3;
        } else if (level < 9) {
            range = LevelRange.L_3_TO_9;
        } else if (level < 27) {
            range = LevelRange.L_9_TO_27;
        } else {
            range = LevelRange.L_27_AND_ABOVE;
        }
        for (int i = 0; i < numberOfQuestions+1; i++) {
            Double randomDouble = Math.random();
            Double sumOfProbabilities = 0.0;
            for (QuestionResponseType type : QuestionResponseType.values()) {
                sumOfProbabilities += range.probabilities[type.ordinal()];
            }
        }
    }

    // 주어진 레벨에 따라 랜덤하게 행동 선택
    public static QuestionResponseType chooseAction(int level) {
        EnumMap<QuestionResponseType, Double> probs = calculateProbabilities(level);
        double random = new Random().nextDouble();
        double cumulativeProbability = 0.0;
        for (QuestionResponseType questionResponseType : QuestionResponseType.values()) {
            cumulativeProbability += probs.get(questionResponseType);
            if (random < cumulativeProbability) {
                return questionResponseType;
            }
        }
        // 이 코드가 실행되면 오류입니다. 확률의 합이 1이 아닌 경우가 있습니다.
        throw new IllegalStateException("Probabilities do not sum up to 1");
    }

     */
}