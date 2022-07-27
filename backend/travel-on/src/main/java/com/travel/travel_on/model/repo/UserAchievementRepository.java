package com.travel.travel_on.model.repo;

import com.travel.travel_on.dto.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievement,Integer> {
    Optional<List<UserAchievement>> findByUserId(int userId);
    Optional<List<UserAchievement>> findByUserIdAndSidoName(int userId,String sidoName);
}
