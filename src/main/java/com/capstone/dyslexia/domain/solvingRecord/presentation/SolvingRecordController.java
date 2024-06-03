package com.capstone.dyslexia.domain.solvingRecord.presentation;

import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.solvingRecord.application.SolvingRecordService;
import com.capstone.dyslexia.domain.solvingRecord.dto.request.SolvingRecordRequestDto;
import com.capstone.dyslexia.domain.solvingRecord.dto.response.SolvingRecordResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/solvingRecord")
@Tag(name = "SolvingRecord", description = "Solving Record API")
public class SolvingRecordController {

    private final SolvingRecordService solvingRecordService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SolvingRecordResponseDto.Response> findSolvingRecordById(
            @RequestHeader Long memberId,
            @RequestHeader Long solvingRecordId
    ) {
        return ResponseEntity.ok(solvingRecordService.findSolvingRecordById(memberId, solvingRecordId));
    }


    @PostMapping(value = "/solve/one/write", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SolvingRecordResponseDto.Response> solveOneWriteQuestion(
            @RequestHeader Long memberId,
            @RequestParam Long questionId,
            @RequestParam QuestionResponseType questionResponseType,
            @RequestHeader String answer
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(solvingRecordService.solveOneQuestion(memberId, SolvingRecordRequestDto.CreateMerged.builder()
                .questionId(questionId)
                .questionResponseType(questionResponseType)
                .answer(answer)
                .build()));
    }

    @PostMapping(value = "/solve/one/read", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SolvingRecordResponseDto.Response> solveOneReadQuestion(
            @RequestHeader Long memberId,
            @RequestParam Long questionId,
            @RequestParam QuestionResponseType questionResponseType,
            @RequestPart MultipartFile answerFile
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(solvingRecordService.solveOneQuestion(memberId, SolvingRecordRequestDto.CreateMerged.builder()
                .questionId(questionId)
                .questionResponseType(questionResponseType)
                .answerFile(answerFile)
                .build()));
    }




}
