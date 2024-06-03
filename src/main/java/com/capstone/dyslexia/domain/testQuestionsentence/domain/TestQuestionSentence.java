package com.capstone.dyslexia.domain.testQuestionsentence.domain;


import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.question.domain.sentence.QuestionSentence;
import com.capstone.dyslexia.domain.solvingRecord.domain.SolvingRecord;
import com.capstone.dyslexia.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "test_question_sentence")
public class TestQuestionSentence extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private QuestionSentence questionSentence;

    @Column(name = "question_response_type")
    private QuestionResponseType questionResponseType;

    @Column(name = "submission_answer_file_path")
    private String submissionAnswerFilePath;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "accuracy_feedback")
    private String accuracyFeedback;

    @Column(name = "speed_feedback")
    private String speedFeedback;

    public void setInfo(SolvingRecord solvingRecord) {
        this.submissionAnswerFilePath = solvingRecord.getSubmissionFilePath();
        this.isCorrect = solvingRecord.getIsCorrect();
        this.accuracyFeedback = solvingRecord.getAccuracyFeedback();
        this.speedFeedback = solvingRecord.getSpeedFeedback();
        this.questionResponseType = solvingRecord.getQuestionResponseType();
    }

}
