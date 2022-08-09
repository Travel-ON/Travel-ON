package com.travel.travel_on.model.repo;

import com.travel.travel_on.entity.Sido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DongRepository extends JpaRepository<Sido,Integer> {
}
