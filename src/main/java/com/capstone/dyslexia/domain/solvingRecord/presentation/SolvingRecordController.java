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
            @RequestHeader Long solvingRecordId,
            @Valid @RequestPart List<SolvingRecordRequestDto.Create> solvingRecordRequestDtoList
    ) {
        return solvingRecordService.findSolvingRecordById(memberId, solvingRecordId);
    }


    @PostMapping(value = "/create/list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<SolvingRecordResponseDto.Create> createSolvingRecordList(
            @RequestHeader Long memberId,
            @Valid @RequestPart List<SolvingRecordRequestDto.Create> solvingRecordRequestDtoList,
            @RequestPart(required = false) List<MultipartFile> answerFileList
    ) {
        if (solvingRecordRequestDtoList.size() != answerFileList.size()) {
            throw new BadRequestException(INVALID_PARAMETER, "문제 풀이 기록과 파일 개수가 일치하지 않습니다.");
        }

        List<SolvingRecordRequestDto.Convert> solvingRecordRequestConvertDtoList = new ArrayList<>();
        for (int i = 0; i < solvingRecordRequestDtoList.size(); i++) {
            SolvingRecordRequestDto.Create requestDto = solvingRecordRequestDtoList.get(i);
            MultipartFile answerFile = answerFileList.get(i);

            QuestionResponseType questionResponseType = QuestionResponseType.valueOf(requestDto.getQuestionResponseType());

            if (questionResponseType.equals(SELECT_WORD) || questionResponseType.equals(WRITE_WORD)) {
                if (answerFile.isEmpty()) throw new BadRequestException(INVALID_PARAMETER, "SELECT WORD와 WRITE WORD는 file을 포함하면 안 됩니다.");
            } else if (questionResponseType.equals(READ_WORD) || questionResponseType.equals(READ_SENTENCE)) {
                if (requestDto.getAnswer().isEmpty()) throw new BadRequestException(INVALID_PARAMETER, "READ WORD와 READ SENTENCE는 answer(String)를 포함하면 안 됩니다.");
            } else throw new BadRequestException(INVALID_PARAMETER, "잘못된 문제 타입입니다");

            solvingRecordRequestConvertDtoList.add(new SolvingRecordRequestDto.Convert(requestDto, answerFile));
        }
        return solvingRecordService.createSolvingRecordList(memberId, solvingRecordRequestConvertDtoList);
    }




}
