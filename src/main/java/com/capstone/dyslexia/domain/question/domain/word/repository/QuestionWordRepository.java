package com.capstone.dyslexia.domain.question.domain.word.repository;

import com.capstone.dyslexia.domain.question.domain.word.QuestionWord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionWordRepository extends JpaRepository<QuestionWord, Long> {

    @Query("SELECT q FROM QuestionWord q")
    Page<QuestionWord> findRandomQuestions(Pageable pageable);
}