package com.capstone.dyslexia.domain.question.domain;

import com.capstone.dyslexia.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "question")
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
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
