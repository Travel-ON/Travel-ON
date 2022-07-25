package com.travel.travel_demo.model.repo;

import com.travel.travel_demo.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
