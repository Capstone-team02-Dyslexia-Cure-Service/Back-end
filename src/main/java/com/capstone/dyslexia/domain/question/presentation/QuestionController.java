package com.capstone.dyslexia.domain.question.presentation;


import com.capstone.dyslexia.domain.question.application.QuestionService;
import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.question.dto.response.QuestionResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
@Tag(name = "Question", description = "Question API")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping(value = "/create_word", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<QuestionResponseDto.CreateWord> createWord(
            @RequestPart String content,
            @RequestPart MultipartFile videoFile
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.createWord(content, videoFile));
    }

    @PostMapping(value = "/create_sentence", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<QuestionResponseDto.CreateSentence> createSentence(
            @RequestPart String content,
            @RequestPart MultipartFile pronunciationFile,
            @RequestPart MultipartFile videoFile
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.createSentence(content, pronunciationFile, videoFile));
    }


    @GetMapping("/id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<QuestionResponseDto.Find> getQuestionById(
            @RequestHeader Long memberId,
            @RequestHeader Long questionId,
            @RequestHeader QuestionResponseType questionResponseType
    ) {
        return ResponseEntity.ok(questionService.getQuestionById(memberId, questionId, questionResponseType));
    }

    @GetMapping("/random_list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<QuestionResponseDto.GetRandom>> getRandomQuestionList(
            @RequestHeader Long memberId,
            @Positive @RequestHeader Long numOfQuestions
    ) {
        return ResponseEntity.ok(questionService.getRandomQuestionList(memberId, numOfQuestions));
    }

    @GetMapping("/random_edu")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<QuestionResponseDto.Find>> getRandomQuestionEduList(
            @RequestHeader Long memberId,
            @Positive @RequestHeader Long numOfQuestions
    ) {
        return ResponseEntity.ok(questionService.getRandomQuestionEduList(memberId, numOfQuestions));
    }

}
