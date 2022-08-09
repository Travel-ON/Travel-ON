package com.travel.travel_on.model.repo;

import com.travel.travel_on.entity.User;
import com.travel.travel_on.entity.VisitPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitPlaceRepository  extends JpaRepository<VisitPlace, Integer> {
    List<VisitPlace> findByUser(User user);
    List<VisitPlace> findByVisitDateBetween(String start, String end);
}

