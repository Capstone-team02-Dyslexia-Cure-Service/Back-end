package com.capstone.dyslexia.domain.solvingRecord.domain.respository;

import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.solvingRecord.domain.SolvingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolvingRecordRepository extends JpaRepository<SolvingRecord, Long> {

    List<SolvingRecord> findSolvingRecordsByMember(Member member);

}
