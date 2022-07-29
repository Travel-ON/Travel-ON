package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.Notice;
import com.travel.travel_on.dto.QNA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QNAService {

    Page<QNA> selectQNA(String keyword, Pageable pageable); // 글 리스트 페이징 조회

    QNA selectOne(Integer id); // 글 조회

    int write(QNA qna); // 글 등록

    int update(QNA qna); // 글 수정

    int delete(Integer id); // 글 삭제

}
