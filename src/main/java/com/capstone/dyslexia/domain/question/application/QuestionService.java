package com.capstone.dyslexia.domain.question.application;

import com.capstone.dyslexia.domain.question.dto.request.CreateQuestionRequestDto;
import com.capstone.dyslexia.domain.question.dto.request.QuestionEducationRequestDto;
import com.capstone.dyslexia.domain.question.dto.request.QuestionRequestDto;
import com.capstone.dyslexia.domain.question.dto.response.QuestionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {
    public QuestionResponseDto create(Long memberId, CreateQuestionRequestDto createQuestionRequestDto) {
        return null;
    }

    public QuestionResponseDto getQuestionById(Long memberId, Long questionId) {
        return null;
    }

    public QuestionResponseDto getRandomQuestion(Long memberId, QuestionRequestDto questionRequestDto) {
        return null;
    }

    public QuestionResponseDto getRandomQuestionList(Long memberId, List<QuestionRequestDto> questionRequestDtoList) {
        return null;
    }

    public QuestionResponseDto getRandomEducation(Long memberId, QuestionEducationRequestDto questionEducationRequestDto) {
        return null;
    }
}
