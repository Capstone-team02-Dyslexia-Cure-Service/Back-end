package com.capstone.dyslexia.domain.solvingRecord.domain.respository;

import com.capstone.dyslexia.domain.solvingRecord.domain.SolvingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolvingRecordRepository extends JpaRepository<SolvingRecord, Long> {
}
