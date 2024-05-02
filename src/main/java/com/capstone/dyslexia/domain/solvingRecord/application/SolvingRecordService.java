package com.capstone.dyslexia.domain.solvingRecord.application;

import com.capstone.dyslexia.domain.solvingRecord.dto.request.SolvingRecordCreateResponseDto;
import com.capstone.dyslexia.domain.solvingRecord.dto.request.SolvingRecordListRequestDto;
import com.capstone.dyslexia.domain.solvingRecord.dto.request.SolvingRecordRequestDto;
import com.capstone.dyslexia.domain.solvingRecord.dto.response.SolvingRecordResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolvingRecordService {
    public SolvingRecordResponseDto findSolvingRecordById(Long memberId, Long solvingRecordId) {
        return null;
    }

    public SolvingRecordCreateResponseDto createSolvingRecord(Long memberId, SolvingRecordRequestDto solvingRecordRequestDto) {
        return null;
    }

    public List<SolvingRecordCreateResponseDto> createSolvingRecordList(Long memberId, @Valid SolvingRecordListRequestDto solvingRecordListRequestDto) {
        return null;
    }
}
