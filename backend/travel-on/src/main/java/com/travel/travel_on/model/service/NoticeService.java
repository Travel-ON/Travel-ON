package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.FAQ;
import com.travel.travel_on.dto.Notice;

import java.util.List;
import java.util.Map;

public interface NoticeService {

    List<Notice> select(); // 글 리스트 조회

    Notice selectOne(Integer id); // 글 조회

    int write(Notice notice); // 글 등록

    int update(Notice notice); // 글 수정

    int delete(Integer id); // 글 삭제

    List<FAQ> selectFAQ(); // FAQ 리스트 조회

//    List<Notice> selectOneFAQ(); // FAQ 글 조회
}
