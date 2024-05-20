package com.capstone.dyslexia.domain.question.dto.response;

import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.question.domain.QuestionType;
import lombok.Builder;

@Builder
public class QuestionResponseDto {

    private Long id;

    private QuestionType questionType;

    private String content;

    private String pronunciationFilePath;

    private String videoPath;

    private QuestionResponseType questionResponseType;

}
