package com.capstone.dyslexia.domain.test.domain;

import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.testQuestionWord.domain.TestQuestionWord;
import com.capstone.dyslexia.domain.testQuestionsentence.domain.TestQuestionSentence;
import com.capstone.dyslexia.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "test")
public class Test extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private Member member;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TestQuestionWord> testQuestionWordList;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TestQuestionSentence> testQuestionSentenceList;

}
