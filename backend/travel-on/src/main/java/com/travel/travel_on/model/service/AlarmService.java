package com.travel.travel_on.model.service;

import com.travel.travel_on.entity.Alarm;
import com.travel.travel_on.entity.User;

import java.util.List;

public interface AlarmService {

    List<Alarm> selectAll(User user);

    void insert(User user, String content);

    void deleteAll(User user);
}
