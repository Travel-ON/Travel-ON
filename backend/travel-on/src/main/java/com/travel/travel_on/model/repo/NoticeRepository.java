package com.travel.travel_on.model.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import com.travel.travel_on.dto.Notice;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    List<Notice> findAll();
}
