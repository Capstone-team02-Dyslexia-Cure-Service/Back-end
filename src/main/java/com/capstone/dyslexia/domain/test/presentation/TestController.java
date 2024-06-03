package com.capstone.dyslexia.domain.test.presentation;

import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.test.dto.TestResponseDto;
import com.capstone.dyslexia.domain.test.application.TestService;
import com.capstone.dyslexia.global.error.ErrorCode;
import com.capstone.dyslexia.global.error.exceptions.BadRequestException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
@Tag(name = "Test", description = "Test API")
public class TestController {

    private final TestService testService;

    @GetMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TestResponseDto.Create> createTest(
            @RequestHeader Long memberId,
            @RequestHeader Long numOfQuestions
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(testService.createTest(memberId, numOfQuestions));
    }

    @PostMapping("/interim_submit/write")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> interimSubmitWrite(
            @RequestHeader Long memberId,
            @RequestParam Long testId,
            @RequestParam Long questionId,
            @RequestParam QuestionResponseType questionResponseType,
            @RequestBody String answer
    ) {
        if (!questionResponseType.equals(QuestionResponseType.SELECT_WORD) && !questionResponseType.equals(QuestionResponseType.WRITE_WORD)) {
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER, "Invalid questionResponseType. It should be SELECT_WORD or WRITE_WORD.");
        }
        return ResponseEntity.ok(testService.interimSubmitWrite(memberId, testId, questionId, questionResponseType, answer));
    }

    @PostMapping("/interim_submit/read")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> interimSubmitRead(
            @RequestHeader Long memberId,
            @RequestParam Long testId,
            @RequestParam Long questionId,
            @RequestParam QuestionResponseType questionResponseType,
            @RequestPart MultipartFile answerFile
    ) {
        if (!questionResponseType.equals(QuestionResponseType.READ_WORD) && !questionResponseType.equals(QuestionResponseType.READ_SENTENCE)) {
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER, "Invalid questionResponseType. It should be READ_WORD or READ_SENTENCE.");
        }
        return ResponseEntity.ok(testService.interimSubmitRead(memberId, testId, questionId, questionResponseType, answerFile));
    }

    @GetMapping("/submit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> submitTest(
            @RequestHeader Long memberId,
            @RequestParam Long testId
    ) {
        return ResponseEntity.ok(testService.submitTest(memberId, testId));
    }

}
