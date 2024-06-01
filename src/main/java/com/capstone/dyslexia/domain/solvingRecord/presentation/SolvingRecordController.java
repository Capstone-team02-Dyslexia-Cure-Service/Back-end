package com.capstone.dyslexia.domain.solvingRecord.presentation;

import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.solvingRecord.application.SolvingRecordService;
import com.capstone.dyslexia.domain.solvingRecord.dto.request.SolvingRecordRequestDto;
import com.capstone.dyslexia.domain.solvingRecord.dto.response.SolvingRecordResponseDto;
import com.capstone.dyslexia.global.error.exceptions.BadRequestException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.capstone.dyslexia.domain.question.domain.QuestionResponseType.*;
import static com.capstone.dyslexia.domain.question.domain.QuestionResponseType.READ_SENTENCE;
import static com.capstone.dyslexia.global.error.ErrorCode.INVALID_PARAMETER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/solvingRecord")
@Tag(name = "SolvingRecord", description = "Solving Record API")
public class SolvingRecordController {

    private final SolvingRecordService solvingRecordService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public SolvingRecordResponseDto.Find findSolvingRecordById(
            @RequestHeader Long memberId,
            @RequestHeader Long solvingRecordId
    ) {
        return solvingRecordService.findSolvingRecordById(memberId, solvingRecordId);
    }


    @PostMapping(value = "/create/list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<SolvingRecordResponseDto.Create> createSolvingRecordList(
            @RequestHeader Long memberId,
            @ModelAttribute("solvingRecordRequestDtoList") List<SolvingRecordRequestDto.Create> solvingRecordRequestDtoList
    ) {
        for (SolvingRecordRequestDto.Create solvingRecordRequestDto : solvingRecordRequestDtoList) {
            if (solvingRecordRequestDto.getQuestionResponseType().equals(READ_WORD.name()) || solvingRecordRequestDto.getQuestionResponseType().equals(READ_SENTENCE.name())) {
                if (solvingRecordRequestDto.getAnswerFile() == null) {
                    throw new BadRequestException(INVALID_PARAMETER, "파일이 없습니다.");
                }
            } else {
                if (solvingRecordRequestDto.getAnswer() == null) {
                    throw new BadRequestException(INVALID_PARAMETER, "답이 없습니다.");
                }
            }
        }

        return solvingRecordService.createSolvingRecordList(memberId, solvingRecordRequestDtoList);
    }




}
