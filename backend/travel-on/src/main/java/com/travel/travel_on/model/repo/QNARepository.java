package com.travel.travel_on.model.repo;

import com.travel.travel_on.entity.QNA;
import com.travel.travel_on.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QNARepository extends JpaRepository<QNA, Integer> {
    List<QNA> findByUser(User user);
}
