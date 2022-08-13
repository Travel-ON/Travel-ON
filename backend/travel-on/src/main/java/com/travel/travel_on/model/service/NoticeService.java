package com.travel.travel_on.model.service;

import com.travel.travel_on.entity.FAQ;
import com.travel.travel_on.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {

    Page<Notice> findPage(Pageable pageable); // 글 리스트 페이징 조회

    Notice selectOne(Integer id); // 글 조회

    boolean write(Notice notice); // 글 등록

    boolean update(Notice notice); // 글 수정

    boolean delete(Integer id); // 글 삭제

    Page<FAQ> search(String keyword, Pageable pageable);

}
