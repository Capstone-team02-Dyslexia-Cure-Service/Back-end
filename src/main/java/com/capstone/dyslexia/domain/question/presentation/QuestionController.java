package com.capstone.dyslexia.domain.question.presentation;


import com.capstone.dyslexia.domain.question.application.QuestionService;
import com.capstone.dyslexia.domain.question.dto.request.CreateQuestionRequestDto;
import com.capstone.dyslexia.domain.question.dto.request.QuestionEducationRequestDto;
import com.capstone.dyslexia.domain.question.dto.request.QuestionRequestDto;
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
    public ApiResponseTemplate<QuestionResponseDto> createQuestion(
            @RequestHeader Long memberId,
            @Valid @RequestBody CreateQuestionRequestDto createQuestionRequestDto) {
        return ApiResponseTemplate.created(questionService.create(memberId, createQuestionRequestDto));
    }

    @GetMapping("/one/{questionId}")
    public ApiResponseTemplate<QuestionResponseDto> getQuestionById(
            @RequestHeader Long memberId,
            @PathVariable Long questionId
    ) {
        return ApiResponseTemplate.ok(questionService.getQuestionById(memberId, questionId));
    }

    @PostMapping("/one/random")
    public ApiResponseTemplate<QuestionResponseDto> getRandomQuestion(
            @RequestHeader Long memberId,
            @Valid @RequestBody QuestionRequestDto questionRequestDto
    ) {
        return ApiResponseTemplate.ok(questionService.getRandomQuestion(memberId, questionRequestDto));
    }

    @PostMapping("/random")
    public ApiResponseTemplate<QuestionResponseDto> getRandomQuestionList(
            @RequestHeader Long memberId,
            @Valid @RequestBody List<QuestionRequestDto> questionRequestDtoList
    ) {
        return ApiResponseTemplate.ok(questionService.getRandomQuestionList(memberId, questionRequestDtoList));
    }

    @PostMapping("/one/education")
    public ApiResponseTemplate<QuestionResponseDto> getRandomEducation(
            @RequestHeader Long memberId,
            @Valid @RequestBody QuestionEducationRequestDto questionEducationRequestDto
    ) {
        return ApiResponseTemplate.ok(questionService.getRandomEducation(memberId, questionEducationRequestDto));
    }
}
