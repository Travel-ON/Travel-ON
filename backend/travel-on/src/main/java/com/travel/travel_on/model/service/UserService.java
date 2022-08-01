package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.entity.User;
import com.travel.travel_on.dto.UserAchievement;
import com.travel.travel_on.dto.Visitation;

import java.util.List;

public interface UserService {
    UserDto select(String id);

    int insert(UserDto userDto);

    int update(UserDto userDto);

    int delete(String id);

    UserDto selectByNickname(String nickname);

    int updateAlarm(int userId);

    List<UserAchievement> selectUserAchievement(int userId, String sidoName);

    int insertUserAchievement(UserAchievement userAchievement);

    List<Visitation> selectVisitation(int userId);

    int updateVisitation(int userId, String sidoName);

    String selectAchievement(int count);

    int sendMail(String mail, String title, String content);
}
