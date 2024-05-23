package com.capstone.dyslexia.domain.question.domain.sentence.repository;

import com.capstone.dyslexia.domain.question.domain.sentence.QuestionSentence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionSentenceRepository extends JpaRepository<QuestionSentence, Long> {

    @Query("SELECT q FROM QuestionSentence q")
    Page<QuestionSentence> findRandomQuestions(Pageable pageable);
}
