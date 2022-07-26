package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.User;

public interface UserService {
    User select(String id);

    int insert(User user);

    int update(User user);

    int delete(String id);

    User selectByNickname(String nickname);
}
