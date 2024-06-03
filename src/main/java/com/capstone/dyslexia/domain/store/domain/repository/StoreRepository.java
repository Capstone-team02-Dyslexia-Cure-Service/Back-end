package com.capstone.dyslexia.domain.store.domain.repository;

import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByMember(Member member);
}
