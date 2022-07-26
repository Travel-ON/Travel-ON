package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.User;
import com.travel.travel_on.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repo;

    @Override
    public User select(String id) {
        Optional<User> result = repo.findByRealId(id);
        if (result.isPresent()) {
            User user = result.get();
            return user;
        }
        return null;
    }

    @Override
    public int insert(User user) {
        Optional<User> result = repo.findByRealId(user.getRealId());
        if (result.isPresent()) {
            return 1;
        } else {
            repo.save(user);
            return 0;
        }
    }

    @Override
    public int update(User user) {
        Optional<User> result = repo.findByRealId(user.getRealId());
        if (result.isPresent()) {
            repo.save(user);
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int delete(String id) {
        Optional<User> result = repo.findByRealId(id);
        if (result.isPresent()) {
            repo.delete(result.get());
            return 0;
        }
        return 1;
    }

    @Override
    public User selectByNickname(String nickname){
        Optional<User> result = repo.findByNickname(nickname);
        if (result.isPresent()) {
            User user = result.get();
            return user;
        }
        return null;
    }
}
