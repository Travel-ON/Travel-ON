package com.travel.travel_on.model.repo;

import com.travel.travel_on.entity.Sido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SidoRepository extends JpaRepository<Sido,Integer> {
    List<Sido> findBySidoCodeStartsWith(String splitCode);
}
