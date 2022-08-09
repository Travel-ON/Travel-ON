package com.travel.travel_on.model.repo;

import com.travel.travel_on.entity.Gugun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GugunRepository extends JpaRepository<Gugun,Integer> {
    List<Gugun> findByGugunCodeStartsWith(String splitCode);
}
