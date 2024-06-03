package com.capstone.dyslexia.domain.question.domain.sentence;

import com.capstone.dyslexia.domain.question.domain.Question;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "question_sentence")
public class QuestionSentence extends Question {

    @Column(name = "content")
    private String content;

    @Column(name = "pronunciation_file_path")
    private String pronunciationFilePath;

    @Builder
    public QuestionSentence(String videoPath, String content, String pronunciationFilePath) {
        super(videoPath);
        this.content = content;
        this.pronunciationFilePath = pronunciationFilePath;
    }

}
