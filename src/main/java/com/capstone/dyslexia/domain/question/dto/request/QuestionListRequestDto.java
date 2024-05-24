package com.capstone.dyslexia.domain.question.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class QuestionListRequestDto {

    List<Long> questionIdList;

}
