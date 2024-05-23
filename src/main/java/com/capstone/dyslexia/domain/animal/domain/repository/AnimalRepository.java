package com.capstone.dyslexia.domain.animal.domain.repository;

import com.capstone.dyslexia.domain.animal.domain.Animal;
import com.capstone.dyslexia.domain.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    public List<Animal> findAllByStore(Store store);
}
