package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.entity.Achievement;
import com.travel.travel_on.entity.User;
import com.travel.travel_on.entity.UserAchievement;
import com.travel.travel_on.entity.Visitation;
import com.travel.travel_on.model.repo.AchievementRepository;
import com.travel.travel_on.model.repo.UserAchievementRepository;
import com.travel.travel_on.model.repo.UserRepository;
import com.travel.travel_on.model.repo.VisitationRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAchievementRepository userAchievementRepository;

    @Autowired
    VisitationRepository visitationRepository;

    @Autowired
    AchievementRepository achievementRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public UserDto select(String id) {
        Optional<User> result = userRepository.findByRealId(id);
        if (result.isPresent()) {
            User user = result.get();
            UserDto userDto = new UserDto(user);
            return userDto;
        }
        return null;
    }

    @Override
    public boolean insert(UserDto userDto) {
        Optional<User> result = userRepository.findByRealId(userDto.getId());
        if (result.isPresent()) {
            return false;
        } else {
            User user = userDto.toEntity();
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public boolean update(UserDto userDto) {
        Optional<User> result = userRepository.findByRealId(userDto.getId());
        if (result.isPresent()) {
            User user = userDto.toEntity();
            if(result.get().getPassword().equals(userDto.getPassword())){
                user.setPassword(result.get().getPassword());
            }else{
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        Optional<User> result = userRepository.findByRealId(id);
        if (result.isPresent()) {
            userRepository.delete(result.get());
            return true;
        }
        return false;
    }

    @Override
    public UserDto selectByNickname(String nickname) {
        Optional<User> result = userRepository.findByNickname(nickname);
        if (result.isPresent()) {
            User user = result.get();
            UserDto userDto = new UserDto(user);
            return userDto;
        }
        return null;
    }

    @Override
    public void updateAlarm(User user) {
        user.setAlarmFlag(true);
        userRepository.save(user);
    }

    @Override
    public List<UserAchievement> selectUserAchievement(User user, String sidoName) {
        List<UserAchievement> list;
        if (sidoName == null){
            list = userAchievementRepository.findByUser(user);
        } else{
            list = userAchievementRepository.findByUserAndSidoName(user, sidoName);
        }

        return list;
    }

    @Override
    public void insertUserAchievement(UserAchievement userAchievement) {
        userAchievementRepository.save(userAchievement);
    }

    @Override
    public List<Visitation> selectVisitation(User user) {
        List<Visitation> list = visitationRepository.findByUser(user);
        return list;
    }

    @Override
    public int updateVisitation(User user, String sidoName) {
        Optional<Visitation> result = visitationRepository.findByUserAndSidoName(user, sidoName);
        Visitation visitation;
        if (result.isPresent()) {
            visitation = result.get();
            visitation.setCount(visitation.getCount() + 1);
            visitationRepository.save(visitation);

        } else {
            visitation = Visitation.builder()
                    .user(user)
                    .sidoName(sidoName)
                    .count(1)
                    .build();
            visitationRepository.save(visitation);
        }
        return visitation.getCount();
    }

    @Override
    public String selectAchievement(int count) {
        Optional<Achievement> result = achievementRepository.findByCount(count);
        if (result.isPresent()) {
            String title = result.get().getTitle();
            return title;
        }
        return null;
    }

    @Override
    public void sendMail(String mail, String title, String content) {
        try {
            SimpleMailMessage simpleMessage = new SimpleMailMessage();

            simpleMessage.setTo(mail);
            simpleMessage.setSubject(title);
            simpleMessage.setText(content);

            javaMailSender.send(simpleMessage);
            log.info("메일 전송");
        } catch (MailException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
