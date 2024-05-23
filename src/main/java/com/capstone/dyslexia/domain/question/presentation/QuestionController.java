package com.capstone.dyslexia.domain.question.presentation;


import com.capstone.dyslexia.domain.question.application.QuestionService;
import com.capstone.dyslexia.domain.question.dto.request.QuestionRequestDto;
import com.capstone.dyslexia.domain.question.dto.response.QuestionResponseDto;
import com.capstone.dyslexia.global.error.exceptions.BadRequestException;
import com.capstone.dyslexia.global.payload.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.capstone.dyslexia.domain.question.domain.QuestionType.SENTENCE;
import static com.capstone.dyslexia.domain.question.domain.QuestionType.WORD;
import static com.capstone.dyslexia.global.error.ErrorCode.INVALID_PARAMETER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
@Tag(name = "Question", description = "Question API")
public class QuestionController {

    private final QuestionService questionService;

    /*
    @PostMapping("/create")
    public ApiResponseTemplate<QuestionResponseDto.Create> createQuestion(
            @RequestHeader Long memberId,
            @Valid @RequestBody QuestionRequestDto.Create questionRequestDto) {
        if (questionRequestDto.getQuestionType().equals(WORD)) {
            return ApiResponseTemplate.created(questionService.create(memberId, questionRequestDto));
        } else if (questionRequestDto.getQuestionType().equals(SENTENCE)) {
            return ApiResponseTemplate.created(questionService.create(memberId, questionRequestDto));
        } else throw new BadRequestException(INVALID_PARAMETER, "잘못된 Question Type 입니다. WORD나 SENTENCE 둘 중 하나여야만 합니다.");
    }


    @GetMapping("/id_list")
    public ApiResponseTemplate<List<QuestionResponseDto.GetById>> getQuestionListById(
            @RequestHeader Long memberId,
            @Valid @RequestBody QuestionRequestDto.GetById questionRequestDto
    ) {
        return ApiResponseTemplate.ok(questionService.getQuestionById(memberId, questionRequestDto));
    }

     */

    @PostMapping("/random_list")
    public ApiResponseTemplate<List<QuestionResponseDto.GetRandom>> getRandomQuestionList(
            @RequestHeader Long memberId,
            @Positive @RequestHeader Long numOfQuestions
    ) {
        return ApiResponseTemplate.ok(questionService.getRandomQuestionList(memberId, numOfQuestions));
    }

}
