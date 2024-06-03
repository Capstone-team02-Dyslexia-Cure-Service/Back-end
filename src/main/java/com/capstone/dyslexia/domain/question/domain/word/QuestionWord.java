package com.capstone.dyslexia.domain.question.domain.word;


import com.capstone.dyslexia.domain.question.domain.Question;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "question_word")
public class QuestionWord extends Question {

    @Column(name = "content")
    private String content;

    @Builder
    public QuestionWord(String videoPath, String content) {
        super(videoPath);
        this.content = content;
    }

}
