package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.QNADto;
import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.entity.QNA;
import com.travel.travel_on.entity.User;

import java.util.List;

public interface QNAService {

    List<QNA> selectAll(User user, String keyword);

    QNADto selectOne(Integer id);

    boolean write(UserDto userDto, QNADto qnaDto);

    boolean update(QNADto qnaDto);

    boolean delete(Integer id);

    List<QNA> adminSelectAll(String keyword);

    List<QNA> noneAnswerAll();

    List<QNA> AnswerAll(User user);
}
