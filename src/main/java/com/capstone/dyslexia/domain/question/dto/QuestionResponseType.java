package com.capstone.dyslexia.domain.question.dto;

import lombok.Getter;

@Getter
public enum QuestionResponseType {
    SELECT_WORD,
    WRITE_WORD,
    READ_WORD,
    READ_SENTENCE;
}
