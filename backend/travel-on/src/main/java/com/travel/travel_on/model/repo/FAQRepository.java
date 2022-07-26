package com.travel.travel_on.model.repo;

import com.travel.travel_on.dto.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FAQRepository extends JpaRepository<FAQ, Integer> {
    List<FAQ> findAll();
}
