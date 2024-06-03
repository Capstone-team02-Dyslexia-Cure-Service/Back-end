package com.capstone.dyslexia.domain.testQuestionWord.domain.repository;

import com.capstone.dyslexia.domain.testQuestionWord.domain.TestQuestionWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestQuestionWordRepository extends JpaRepository<TestQuestionWord, Long> {
}
