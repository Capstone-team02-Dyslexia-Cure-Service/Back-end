package com.capstone.dyslexia.domain.solvingRecord.presentation;

import com.capstone.dyslexia.domain.solvingRecord.application.SolvingRecordService;
import com.capstone.dyslexia.domain.solvingRecord.dto.response.SolvingRecordCreateResponseDto;
import com.capstone.dyslexia.domain.solvingRecord.dto.request.SolvingRecordRequestDto;
import com.capstone.dyslexia.domain.solvingRecord.dto.response.SolvingRecordResponseDto;
import com.capstone.dyslexia.global.payload.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/solvingRecord")
@Tag(name = "SolvingRecord", description = "Solving Record API")
public class SolvingRecordController {

    private final SolvingRecordService solvingRecordService;

    @GetMapping
    public ApiResponseTemplate<SolvingRecordResponseDto> findSolvingRecordById(
            @RequestHeader Long memberId,
            @RequestHeader Long solvingRecordId
    ) {
        return ApiResponseTemplate.ok(solvingRecordService.findSolvingRecordById(memberId, solvingRecordId));
    }

    @PostMapping("/create/list")
    public ApiResponseTemplate<List<SolvingRecordCreateResponseDto>> createSolvingRecordList(
            @RequestHeader Long memberId,
            @Valid @RequestPart List<SolvingRecordRequestDto> solvingRecordRequestDtoList
    ) {
        return ApiResponseTemplate.created(solvingRecordService.createSolvingRecordList(memberId, solvingRecordRequestDtoList));
    }
}
