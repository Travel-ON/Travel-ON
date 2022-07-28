package com.travel.travel_on.model.repo;

import com.travel.travel_on.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByRealId(String realId);
}
