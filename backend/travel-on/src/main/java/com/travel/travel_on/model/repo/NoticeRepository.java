package com.travel.travel_on.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.travel_on.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
}
