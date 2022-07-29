package com.travel.travel_on.model.repo;

import com.travel.travel_on.dto.Visitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitationRepository extends JpaRepository<Visitation,Integer> {
    Optional<List<Visitation>> findByUserId(int userId);
    Optional<Visitation> findByUserIdAndSidoName(int userId,String sidoName);
}
