package com.capstone.dyslexia.domain.testQuestionWord.domain;

import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.question.domain.word.QuestionWord;
import com.capstone.dyslexia.domain.solvingRecord.domain.SolvingRecord;
import com.capstone.dyslexia.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "test_question_word")
public class TestQuestionWord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private QuestionWord questionWord;

    @Column(name = "question_response_type")
    private QuestionResponseType questionResponseType;

    @Column(name = "submission_answer")
    private String submissionAnswer;

    @Column(name = "submission_file_path")
    private String submissionFilePath;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "accuracy_feedback")
    private String accuracyFeedback;

    public void setInfo(SolvingRecord solvingRecord) {
        this.questionResponseType = solvingRecord.getQuestionResponseType();
        if (solvingRecord.getQuestionResponseType().equals(QuestionResponseType.READ_WORD)) {
            this.submissionFilePath = solvingRecord.getSubmissionFilePath();
        } else {
            this.submissionAnswer = solvingRecord.getSubmissionAnswer();
        }
        this.isCorrect = solvingRecord.getIsCorrect();
        this.accuracyFeedback = solvingRecord.getAccuracyFeedback();
    }

}
