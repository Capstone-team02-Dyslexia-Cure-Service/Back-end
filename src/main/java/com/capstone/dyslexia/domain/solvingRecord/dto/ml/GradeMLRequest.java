package com.capstone.dyslexia.domain.solvingRecord.dto.ml;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public class GradeMLRequest {

    private MultipartFile answerFile;

    private String questionSavedAnswerFilePath;

}
