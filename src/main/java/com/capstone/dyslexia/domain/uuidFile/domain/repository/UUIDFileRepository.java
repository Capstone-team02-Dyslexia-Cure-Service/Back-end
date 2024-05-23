package com.capstone.dyslexia.domain.uuidFile.domain.repository;

import com.capstone.dyslexia.domain.uuidFile.domain.UUIDFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UUIDFileRepository extends JpaRepository<UUIDFile, Long> {
}
