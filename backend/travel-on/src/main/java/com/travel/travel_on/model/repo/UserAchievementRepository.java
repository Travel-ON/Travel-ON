package com.travel.travel_on.model.repo;

import com.travel.travel_on.entity.User;
import com.travel.travel_on.entity.UserAchievement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievement,Integer> {
    List<UserAchievement> findByUser(User user);
    List<UserAchievement> findByUserAndSidoName(User user,String sidoName);
}
