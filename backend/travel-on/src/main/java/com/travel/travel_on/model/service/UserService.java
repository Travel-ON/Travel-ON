package com.travel.travel_on.model.service;

import com.travel.travel_on.entity.User;
import com.travel.travel_on.dto.UserAchievement;
import com.travel.travel_on.dto.Visitation;

import java.util.List;

public interface UserService {
    User select(String id);

    int insert(User user);

    int update(User user);

    int delete(String id);

    User selectByNickname(String nickname);

    int updateAlarm(int userId);

    List<UserAchievement> selectUserAchievement(int userId, String sidoName);

    int insertUserAchievement(UserAchievement userAchievement);

    List<Visitation> selectVisitation(int userId);

    int updateVisitation(int userId, String sidoName);

    String selectAchievement(int count);

    int sendMail(String mail, String title, String content);
}
