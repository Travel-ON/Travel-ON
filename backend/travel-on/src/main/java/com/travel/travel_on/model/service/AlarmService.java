package com.travel.travel_on.model.service;

import com.travel.travel_on.entity.Alarm;
import com.travel.travel_on.entity.User;

import java.util.List;

public interface AlarmService {

    List<Alarm> selectAll(User user);

    int insert(User user, String content);
//    int insert(int userId, String content);

    int deleteAll(User user);
}
