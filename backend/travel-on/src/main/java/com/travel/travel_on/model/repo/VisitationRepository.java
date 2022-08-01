package com.travel.travel_on.model.repo;

import com.travel.travel_on.entity.User;
import com.travel.travel_on.entity.Visitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitationRepository extends JpaRepository<Visitation,Integer> {
    List<Visitation> findByUser(User user);
    Optional<Visitation> findByUserAndSidoName(User user,String sidoName);
}
