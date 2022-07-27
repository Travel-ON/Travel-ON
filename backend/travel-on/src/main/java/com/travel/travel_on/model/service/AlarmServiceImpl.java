package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.Alarm;
import com.travel.travel_on.model.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    AlarmRepository repo;

    @Autowired
    UserService usvc;

    @Override
    public List<Alarm> selectAll(int userId) {
        Optional<List<Alarm>> result = repo.findByUserId(userId);
        if (result.isPresent()) {
            List<Alarm> list = result.get();
            return list;
        }
        return null;
    }

    @Override
    public int insert(int userId,String content) {
        Alarm alarm = Alarm.builder()
                .userId(userId)
                .content(content)
                .build();
        repo.save(alarm);
        usvc.updateAlarm(userId);
        return 0;
    }

    @Override
    public int deleteAll(int userId) {
        Optional<List<Alarm>> result = repo.findByUserId(userId);
        if (result.isPresent()) {
            for(Alarm alarm : result.get()) {
                repo.delete(alarm);
            }
            return 0;
        }
        return 1;
    }
}
