package com.capstone.dyslexia.domain.solvingRecord.dto.ml;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class GradeMLResponse {

    private Boolean isCorrect;

    private MultipartFile answerFile;

    // 단어 쓰기에선 쓰기, 읽기고
    // 문장에서는 속도까지.

}
