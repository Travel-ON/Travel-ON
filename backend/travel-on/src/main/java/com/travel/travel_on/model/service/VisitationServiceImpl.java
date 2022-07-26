package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.User;
import com.travel.travel_on.dto.Visitation;
import com.travel.travel_on.model.repo.VisitationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class VisitationServiceImpl implements VisitationService{

    @Autowired
    VisitationRepository repo;

    @Override
    public List<Visitation> select(int user_id) {
        Optional<Visitation> result = repo.findById(user_id);
        if (result.isPresent()) {
//            User user = result.get();
//            return user;
        }
        return null;
    }
}
