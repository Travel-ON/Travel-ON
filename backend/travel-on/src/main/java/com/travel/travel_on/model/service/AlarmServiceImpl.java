package com.travel.travel_on.model.service;

import com.travel.travel_on.entity.Alarm;
import com.travel.travel_on.entity.User;
import com.travel.travel_on.model.repo.AlarmRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    AlarmRepository alarmRepository;

    @Autowired
    UserService userService;

    @Override
    public List<Alarm> selectAll(User user) {
        List<Alarm> list = alarmRepository.findByUser(user);
        return list;
    }

    @Override
    public void insert(User user, String content) {
        Alarm alarm = Alarm.builder()
                .user(user)
                .content(content)
                .build();
        alarm.setUser(user);
        alarmRepository.save(alarm);
        userService.updateAlarm(user);
    }


    @Override
    public void deleteAll(User user) {
        List<Alarm> list = alarmRepository.findByUser(user);
            for(Alarm alarm : list) {
                alarmRepository.delete(alarm);
            }
    }
}
