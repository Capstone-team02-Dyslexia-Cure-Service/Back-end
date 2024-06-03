package com.capstone.dyslexia.domain.test.domain.repository;

import com.capstone.dyslexia.domain.test.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findByMemberId(Long memberId);

}
