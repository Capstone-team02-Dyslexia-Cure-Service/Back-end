package com.capstone.dyslexia.domain.question.presentation;


import com.capstone.dyslexia.domain.question.application.QuestionService;
import com.capstone.dyslexia.domain.question.dto.response.QuestionResponseDto;
import com.capstone.dyslexia.global.payload.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
@Tag(name = "Question", description = "Question API")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/create_word")
    public ApiResponseTemplate<QuestionResponseDto.CreateWord> createWord(
            @RequestHeader Long memberId,
            @RequestBody String content
    ) {
        return ApiResponseTemplate.created(questionService.createWord(memberId, content));
    }

    @PostMapping("/create_sentence")
    public ApiResponseTemplate<QuestionResponseDto.CreateSentence> createSentence(
            @RequestHeader Long memberId,
            @RequestBody String content,
            @RequestPart MultipartFile pronunciationFile,
            @RequestPart MultipartFile videoFile
    ) {
        return ApiResponseTemplate.created(questionService.createSentence(memberId, content, pronunciationFile, videoFile));
    }


    @GetMapping("/id_list")
    public ApiResponseTemplate<QuestionResponseDto.Find> getQuestionById(
            @RequestHeader Long memberId,
            @RequestHeader Long questionId
    ) {
        return ApiResponseTemplate.ok(questionService.getQuestionById(memberId, questionId));
    }

    @GetMapping("/random_list")
    public ApiResponseTemplate<List<QuestionResponseDto.GetRandom>> getRandomQuestionList(
            @RequestHeader Long memberId,
            @Positive @RequestHeader Long numOfQuestions
    ) {
        return ApiResponseTemplate.ok(questionService.getRandomQuestionList(memberId, numOfQuestions));
    }

    @GetMapping("/random_edu")
    public ApiResponseTemplate<List<QuestionResponseDto.Find>> getRandomQuestionEduList(
            @RequestHeader Long memberId,
            @Positive @RequestHeader Long numOfQuestions
    ) {
        return ApiResponseTemplate.ok(questionService.getRandomQuestionEduList(memberId, numOfQuestions));
    }

}
