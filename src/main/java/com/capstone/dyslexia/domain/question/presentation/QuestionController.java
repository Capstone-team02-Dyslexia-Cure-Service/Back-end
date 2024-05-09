package com.capstone.dyslexia.domain.question.presentation;


import com.capstone.dyslexia.domain.question.application.QuestionService;
import com.capstone.dyslexia.domain.question.dto.request.CreateQuestionRequestDto;
import com.capstone.dyslexia.domain.question.dto.request.QuestionListRequestDto;
import com.capstone.dyslexia.domain.question.dto.request.RandomQuestionRequestDto;
import com.capstone.dyslexia.domain.question.dto.response.CreateQuestionResponseDto;
import com.capstone.dyslexia.domain.question.dto.response.QuestionResponseDto;
import com.capstone.dyslexia.global.payload.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
@Tag(name = "Question", description = "Question API")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/create")
    public ApiResponseTemplate<CreateQuestionResponseDto> createQuestion(
            @RequestHeader Long memberId,
            @Valid @RequestBody CreateQuestionRequestDto createQuestionRequestDto) {
        return ApiResponseTemplate.created(questionService.create(memberId, createQuestionRequestDto));
    }

    @GetMapping("/id_list")
    public ApiResponseTemplate<List<CreateQuestionResponseDto>> getQuestionListById(
            @RequestHeader Long memberId,
            @Valid @RequestBody QuestionListRequestDto questionListRequestDto
    ) {
        return ApiResponseTemplate.ok(questionService.getQuestionById(memberId, questionListRequestDto));
    }

    @PostMapping("/random_list")
    public ApiResponseTemplate<List<QuestionResponseDto>> getRandomQuestionList(
            @RequestHeader Long memberId,
            @Valid @RequestBody RandomQuestionRequestDto randomQuestionRequestDto
    ) {
        return ApiResponseTemplate.ok(questionService.getRandomQuestionList(memberId, randomQuestionRequestDto));
    }
}
