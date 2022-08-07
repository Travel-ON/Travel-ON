package com.travel.travel_on.model.service;

import com.travel.travel_on.entity.FAQ;
import com.travel.travel_on.entity.Notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {

    Page<Notice> findPage(Pageable pageable);

    Notice selectOne(Integer id);

    boolean write(Notice notice);

    boolean update(Notice notice);

    boolean delete(Integer id);

    Page<FAQ> search(String keyword, Pageable pageable);

}
