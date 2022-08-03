package com.travel.travel_on.model.repo;

import com.travel.travel_on.dto.Achievement;
import com.travel.travel_on.dto.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement,Integer> {
    Optional<Achievement> findByCount(int count);
}
