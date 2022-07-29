package com.travel.travel_on.model.repo;

import com.travel.travel_on.dto.QNA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QNARepository extends JpaRepository<QNA, Integer> {
    Page<QNA> findByTitleContaining(String keyword, Pageable pageable);
}
