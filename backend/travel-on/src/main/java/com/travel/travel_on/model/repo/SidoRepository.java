package com.travel.travel_on.model.repo;

import com.travel.travel_on.entity.Sido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SidoRepository extends JpaRepository<Sido,Integer> {
    List<Sido> findBySidoCodeStartsWith(String splitCode);
    Optional<Sido> findFirstBySidoNameContaining(String sido);
}
