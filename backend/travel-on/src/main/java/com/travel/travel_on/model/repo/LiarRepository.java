package com.travel.travel_on.model.repo;

import com.travel.travel_on.entity.Liar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LiarRepository extends JpaRepository<Liar,Integer> {
    @Query(value = "SELECT distinct topic from liar", nativeQuery = true)
    List<String> findDistinctAll();

    @Query(value = "SELECT keyword from liar where topic=?1", nativeQuery = true)
    Optional<List<String>> findDistinctByTopic(String topic);
}
