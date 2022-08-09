package com.travel.travel_on.model.repo;

import com.travel.travel_on.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepositorty extends JpaRepository<Place, Integer> {
    List<Place> findByVisitPlaceContaining(String keyword);
    Optional<Place> findFirstByVisitPlaceContaining(String visit);
}

