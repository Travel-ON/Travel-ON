package com.travel.travel_on.model.repo;

import com.travel.travel_on.dto.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm,Integer> {
    Optional<List<Alarm>> findByUserId(int userId);
}
