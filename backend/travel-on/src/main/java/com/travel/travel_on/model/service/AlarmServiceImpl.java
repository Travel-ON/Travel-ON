package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.entity.Alarm;
import com.travel.travel_on.entity.User;
import com.travel.travel_on.model.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    AlarmRepository repo;

    @Autowired
    UserService usvc;

    @Override
    public List<Alarm> selectAll(User user) {
        List<Alarm> list = repo.findByUser(user);
        return list;
    }

    @Override
    public int insert(UserDto userDto, String content) {
        User user = userDto.toEntity();
        System.out.println("<<<<<<<<<<<<");
        System.out.println(user.getAlarms());
        System.out.println("<<<<<<<<<<<<");

        Alarm alarm = Alarm.builder()
                .user(user)
                .content(content)
                .build();
        alarm.setUser(user);
        repo.save(alarm);
        usvc.updateAlarm(user.getUserId());
        return 0;
    }


    @Override
    public int deleteAll(User user) {
        List<Alarm> list = repo.findByUser(user);
            for(Alarm alarm : list) {
                repo.delete(alarm);
            }
            return 0;
    }
}
