package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.entity.User;
import com.travel.travel_on.entity.UserAchievement;
import com.travel.travel_on.entity.Visitation;

import java.util.List;

public interface UserService {
    UserDto select(String id);

    int insert(UserDto userDto);

    int update(UserDto userDto);

    int delete(String id);

    UserDto selectByNickname(String nickname);

    int updateAlarm(int userId);

    List<UserAchievement> selectUserAchievement(User user, String sidoName);

    int insertUserAchievement(UserAchievement userAchievement);

    List<Visitation> selectVisitation(User user);

    int updateVisitation(User user, String sidoName);

    String selectAchievement(int count);

    int sendMail(String mail, String title, String content);
}
