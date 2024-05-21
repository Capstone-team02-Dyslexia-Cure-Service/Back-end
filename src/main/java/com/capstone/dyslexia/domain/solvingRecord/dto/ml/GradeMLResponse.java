package com.capstone.dyslexia.domain.solvingRecord.dto.ml;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class GradeMLResponse {

    private Boolean isCorrect;

    private MultipartFile answerFile;

}
