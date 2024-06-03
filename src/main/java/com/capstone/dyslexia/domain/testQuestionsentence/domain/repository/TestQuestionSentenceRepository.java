package com.capstone.dyslexia.domain.testQuestionsentence.domain.repository;

import com.capstone.dyslexia.domain.testQuestionsentence.domain.TestQuestionSentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestQuestionSentenceRepository extends JpaRepository<TestQuestionSentence, Long> {
}
