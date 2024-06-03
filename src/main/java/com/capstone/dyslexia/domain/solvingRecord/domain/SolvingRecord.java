package com.capstone.dyslexia.domain.solvingRecord.domain;

import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.question.domain.sentence.QuestionSentence;
import com.capstone.dyslexia.domain.question.domain.word.QuestionWord;
import com.capstone.dyslexia.domain.testQuestionWord.domain.TestQuestionWord;
import com.capstone.dyslexia.domain.testQuestionsentence.domain.TestQuestionSentence;
import com.capstone.dyslexia.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "solving_record")
public class SolvingRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "submission_answer")
    private String submissionAnswer;

    @Column(name = "submission_file_path")
    private String submissionFilePath;

    @Column(name = "accuracy_feedback")
    private String accuracyFeedback;

    @Column(name = "speed_feedback")
    private String speedFeedback;

    @Column(name = "question_response_type")
    private QuestionResponseType questionResponseType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_word_id")
    private QuestionWord questionWord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_sentence_id")
    private QuestionSentence questionSentence;

    public static SolvingRecord convertTestQuestionWordToSolvingRecord(TestQuestionWord testQuestionWord, Member member) {
        if (testQuestionWord.getQuestionResponseType().equals(QuestionResponseType.READ_WORD)) {
            return SolvingRecord.builder()
                    .isCorrect(testQuestionWord.getIsCorrect())
                    .submissionFilePath(testQuestionWord.getSubmissionFilePath())
                    .accuracyFeedback(testQuestionWord.getAccuracyFeedback())
                    .questionResponseType(testQuestionWord.getQuestionResponseType())
                    .member(member)
                    .questionWord(testQuestionWord.getQuestionWord())
                    .build();
        } else {
            return SolvingRecord.builder()
                    .isCorrect(testQuestionWord.getIsCorrect())
                    .submissionAnswer(testQuestionWord.getSubmissionAnswer())
                    .accuracyFeedback(testQuestionWord.getAccuracyFeedback())
                    .questionResponseType(QuestionResponseType.WRITE_WORD)
                    .member(member)
                    .questionWord(testQuestionWord.getQuestionWord())
                    .build();
        }
    }

    public static SolvingRecord convertTestQuestionSentenceToSolvingRecord(TestQuestionSentence testQuestionSentence, Member member) {
        return SolvingRecord.builder()
                .isCorrect(testQuestionSentence.getIsCorrect())
                .submissionFilePath(testQuestionSentence.getSubmissionAnswerFilePath())
                .accuracyFeedback(testQuestionSentence.getAccuracyFeedback())
                .speedFeedback(testQuestionSentence.getSpeedFeedback())
                .questionResponseType(testQuestionSentence.getQuestionResponseType())
                .member(member)
                .questionSentence(testQuestionSentence.getQuestionSentence())
                .build();
    }
}
