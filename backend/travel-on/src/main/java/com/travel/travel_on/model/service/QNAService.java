package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.QNADto;
import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.entity.QNA;
import com.travel.travel_on.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QNAService {

    List<QNA> selectAll(User user, String keyword); // 글 리스트 전체 조회

    QNADto selectOne(Integer id); // 글 조회

    int write(UserDto userDto, QNADto qnaDto); // 글 등록

    int update(QNADto qnaDto); // 글 수정

    int delete(Integer id); // 글 삭제

}
