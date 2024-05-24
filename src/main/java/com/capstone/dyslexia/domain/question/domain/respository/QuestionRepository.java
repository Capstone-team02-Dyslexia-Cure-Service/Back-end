package com.capstone.dyslexia.domain.question.domain.respository;


import com.capstone.dyslexia.domain.question.domain.Question;
import com.capstone.dyslexia.domain.question.domain.QuestionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    public Long countByQuestionType(QuestionType questionType);

    Page<Question> findAllByQuestionType(QuestionType questionType, PageRequest pageRequest);
}
