package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.Visitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitationService {

    List<Visitation> select(int user_id);
}