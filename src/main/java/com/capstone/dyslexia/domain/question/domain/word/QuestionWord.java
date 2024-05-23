package com.capstone.dyslexia.domain.question.domain.word;


import com.capstone.dyslexia.domain.question.domain.Question;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "question_word")
public class QuestionWord extends Question {

    @Column(name = "content")
    private String content;

}
