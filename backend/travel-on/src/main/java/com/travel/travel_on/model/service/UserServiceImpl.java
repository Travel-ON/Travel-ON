package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.*;
import com.travel.travel_on.model.repo.AchievementRepository;
import com.travel.travel_on.model.repo.UserAchievementRepository;
import com.travel.travel_on.model.repo.UserRepository;
import com.travel.travel_on.model.repo.VisitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repo;

    @Autowired
    UserAchievementRepository uarepo;

    @Autowired
    VisitationRepository vrepo;

    @Autowired
    AchievementRepository arepo;

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
    public User selectByNickname(String nickname) {
        Optional<User> result = repo.findByNickname(nickname);
        if (result.isPresent()) {
            User user = result.get();
            return user;
        }
        return null;
    }

    @Override
    public int updateAlarm(int userId) {
        Optional<User> result = repo.findById(userId);
        if (result.isPresent()) {
            User user = result.get();
            user.setAlarmFlag(true);
            repo.save(user);
            return 0;
        }
        return 1;
    }

    @Override
    public List<UserAchievement> selectUserAchievement(int userId, String sidoName) {
        Optional<List<UserAchievement>> result;
        if (sidoName == null) result = uarepo.findByUserId(userId);
        else result = uarepo.findByUserIdAndSidoName(userId, sidoName);

        if (result.isPresent()) {
            List<UserAchievement> list = result.get();
            return list;
        }
        return null;
    }

    @Override
    public int insertUserAchievement(UserAchievement userAchievement) {
        uarepo.save(userAchievement);
        return 0;
    }

    @Override
    public List<Visitation> selectVisitation(int userId) {
        Optional<List<Visitation>> result = vrepo.findByUserId(userId);
        if (result.isPresent()) {
            List<Visitation> list = result.get();
            return list;
        }
        return null;
    }

    @Override
    public int updateVisitation(int userId, String sidoName) {
        Optional<Visitation> result = vrepo.findByUserIdAndSidoName(userId, sidoName);
        Visitation visitation;
        if (result.isPresent()) {
            //업데이트
            visitation = result.get();
            visitation.setCount(visitation.getCount() + 1);
            vrepo.save(visitation);

        } else {
            //생성
            visitation = Visitation.builder()
                    .userId(userId)
                    .sidoName(sidoName)
                    .count(1)
                    .build();
            vrepo.save(visitation);
        }
        return visitation.getCount();
    }

    @Override
    public String selectAchievement(int count) {
        Optional<Achievement> result = arepo.findByCount(count);
        if (result.isPresent()) {
            String title = result.get().getTitle();
            return title;
        }
        return null;
    }
}
