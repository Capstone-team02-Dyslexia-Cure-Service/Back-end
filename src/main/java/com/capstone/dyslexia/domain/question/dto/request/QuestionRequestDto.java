package com.capstone.dyslexia.domain.question.dto.request;

import com.capstone.dyslexia.domain.question.domain.QuestionType;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
public class QuestionRequestDto {

    private Long id;

    private QuestionType questionType;

    @Column(name = "content")
    private String content;

    @Column(name = "pronunciation_file_path")
    private String pronunciationFilePath;

    @Column(name = "video_path")
    private String videoPath;

    @Column(name = "difficulty")
    private Double difficulty;

}
