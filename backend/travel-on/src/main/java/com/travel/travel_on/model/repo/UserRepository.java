package com.travel.travel_on.model.repo;

import com.travel.travel_on.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByRealId(String realId);
    Optional<User> findByNickname(String nickname);
}

