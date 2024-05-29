package com.capstone.dyslexia.domain.question.presentation;


import com.capstone.dyslexia.domain.question.application.QuestionService;
import com.capstone.dyslexia.domain.question.dto.response.QuestionResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionResponseDto.CreateWord createWord(
            @RequestBody String content
    ) {
        return questionService.createWord(content);
    }

    @PostMapping(value = "/create_sentence", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionResponseDto.CreateSentence createSentence(
            @RequestPart String content,
            @RequestPart(required = false) MultipartFile pronunciationFile,
            @RequestPart(required = false) MultipartFile videoFile
    ) {
        return questionService.createSentence(content, pronunciationFile, videoFile);
    }


    @GetMapping("/id")
    @ResponseStatus(HttpStatus.OK)
    public QuestionResponseDto.Find getQuestionById(
            @RequestHeader Long memberId,
            @RequestHeader Long questionId
    ) {
        return questionService.getQuestionById(memberId, questionId);
    }

    @GetMapping("/random_list")
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionResponseDto.GetRandom> getRandomQuestionList(
            @RequestHeader Long memberId,
            @Positive @RequestHeader Long numOfQuestions
    ) {
        return questionService.getRandomQuestionList(memberId, numOfQuestions);
    }

    @GetMapping("/random_edu")
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionResponseDto.Find> getRandomQuestionEduList(
            @RequestHeader Long memberId,
            @Positive @RequestHeader Long numOfQuestions
    ) {
        return questionService.getRandomQuestionEduList(memberId, numOfQuestions);
    }

}
