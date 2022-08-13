package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.entity.User;
import com.travel.travel_on.entity.UserAchievement;
import com.travel.travel_on.entity.Visitation;

import java.util.List;

public interface UserService {
    UserDto select(String id);

    boolean insert(UserDto userDto);

    boolean update(UserDto userDto);

    boolean delete(String id);

    UserDto selectByNickname(String nickname);

    void updateAlarm(User user);

    List<UserAchievement> selectUserAchievement(User user, String sidoName);

    void insertUserAchievement(UserAchievement userAchievement);

    List<Visitation> selectVisitation(User user);

    int updateVisitation(User user, String sidoName);

    String selectAchievement(int count);

    void sendMail(String mail, String title, String content);
}
