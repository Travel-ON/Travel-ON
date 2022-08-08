package com.travel.travel_on.model.repo;

import com.travel.travel_on.entity.User;
import com.travel.travel_on.entity.VisitExpected;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitExpectedRepository extends JpaRepository<VisitExpected, Integer> {
    List<VisitExpected> findByUser(User user);

}
