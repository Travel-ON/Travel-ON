package com.travel.travel_on.model.repo;

import com.travel.travel_on.entity.Alarm;
import com.travel.travel_on.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm,Integer> {
    List<Alarm> findByUser(User user);
}
