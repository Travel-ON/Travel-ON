package com.travel.travel_on.model.repo;

import com.travel.travel_on.entity.FAQ;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FAQRepository extends JpaRepository<FAQ, Integer> {
    List<FAQ> findAll();
    Page<FAQ> findByQuestionContaining(String keyword, Pageable pageable);
}
