package com.travel.travel_on.model.service;

import com.travel.travel_on.entity.*;
import com.travel.travel_on.model.repo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class VideoChattingRoomServiceImpl implements VideoChattingRoomService{

    @Autowired
    VideoChattingRoomRepository videoChattingRoomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserVideoChattingRoomRepository userVideoChattingRoomRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    LiarRepository liarRepository;

    @Override
    public boolean checkRoomNumber(String roomCode) {
        VideoChattingRoom result = videoChattingRoomRepository.findByRoomCode(roomCode);
        if(result==null) return true;
        return false;
    }

    @Override
    public boolean insert(VideoChattingRoom videoChattingRoom) {
        User user = videoChattingRoom.getUser();

        if(userVideoChattingRoomRepository.findByUser(user)!=null) {
            return false;
        }
        VideoChattingRoom result = videoChattingRoomRepository.save(videoChattingRoom);

        UserVideoChattingRoom userVideoChattingRoom = new UserVideoChattingRoom();
        userVideoChattingRoom.setUser(user);
        userVideoChattingRoom.setVideoChattingRoom(result);

        userVideoChattingRoomRepository.save(userVideoChattingRoom);
        return true;
    }

    @Override
    public boolean update(String roomCode, boolean status) {
        VideoChattingRoom videoChattingRoom = videoChattingRoomRepository.findByRoomCode(roomCode);
        videoChattingRoom.setPlayGame(status);
        videoChattingRoomRepository.save(videoChattingRoom);
        return true;
    }

    @Override
    public VideoChattingRoom match(User user, String areaCode) {

        if(userVideoChattingRoomRepository.findByUser(user)!=null) {
            return null;
        }

        List<VideoChattingRoom> videoChattingRooms = videoChattingRoomRepository.findByAreaCode(areaCode);
        VideoChattingRoom videoChattingRoom = null;

        while(videoChattingRooms!=null && 0 < videoChattingRooms.size() && videoChattingRoom==null){
            int index = ThreadLocalRandom.current().nextInt(0, videoChattingRooms.size());
            videoChattingRoom = videoChattingRooms.get(index);
            if(videoChattingRoom.getCount()==userVideoChattingRoomRepository.findByVideoChattingRoom(videoChattingRoom).size()){
                videoChattingRooms.remove(index);
                videoChattingRoom=null;
            }
        }
        if(videoChattingRoom!=null){
            UserVideoChattingRoom userVideoChattingRoom = new UserVideoChattingRoom();
            userVideoChattingRoom.setUser(user);
            userVideoChattingRoom.setVideoChattingRoom(videoChattingRoom);

            userVideoChattingRoomRepository.save(userVideoChattingRoom);
        }
        return videoChattingRoom;
    }

    @Override
    public VideoChattingRoom enter(User user, String roomCode) {
        if(userVideoChattingRoomRepository.findByUser(user)!=null) {
            return null;
        }
        VideoChattingRoom videoChattingRoom = videoChattingRoomRepository.findByRoomCode(roomCode);

        if(videoChattingRoom==null ||
                videoChattingRoom.getCount()==userVideoChattingRoomRepository.findByVideoChattingRoom(videoChattingRoom).size()){
            return null;
        }
        UserVideoChattingRoom userVideoChattingRoom = new UserVideoChattingRoom();
        userVideoChattingRoom.setUser(user);
        userVideoChattingRoom.setVideoChattingRoom(videoChattingRoom);
        userVideoChattingRoomRepository.save(userVideoChattingRoom);
        return videoChattingRoom;
    }

    @Override
    public boolean leave(User user, String roomCode) {
        VideoChattingRoom videoChattingRoom = videoChattingRoomRepository.findByRoomCode(roomCode);
        if(videoChattingRoom==null) {
            return false;
        }

        UserVideoChattingRoom userVideoChattingRoom = userVideoChattingRoomRepository.findByUser(user);
        if(userVideoChattingRoomRepository.findByUser(user)==null ||
                !userVideoChattingRoom.getVideoChattingRoom().getRoomCode().equals(roomCode) ||
                videoChattingRoom.getUser().getUserId()==user.getUserId()){
            return false;
        }

        userVideoChattingRoomRepository.delete(userVideoChattingRoom);
        return true;
    }

    @Override
    public boolean close(User user, String roomCode) {
        VideoChattingRoom videoChattingRoom = videoChattingRoomRepository.findByRoomCode(roomCode);
        if(videoChattingRoom==null) {
            return false;
        }

        UserVideoChattingRoom userVideoChattingRoom = userVideoChattingRoomRepository.findByUser(user);
        if(userVideoChattingRoomRepository.findByUser(user)==null ||
                !userVideoChattingRoom.getVideoChattingRoom().getRoomCode().equals(roomCode) ||
                videoChattingRoom.getUser().getUserId()!=user.getUserId()) {
            return false;
        }
        userVideoChattingRoomRepository.delete(userVideoChattingRoom);
        videoChattingRoomRepository.delete(videoChattingRoom);
        return true;
    }

    @Override
    public boolean report(Report report) {
        Optional<User> result = userRepository.findByRealId(report.getReportedId());
        if (result.isPresent()) {
            reportRepository.save(report);
            User user = result.get();
            user.setReportCount(user.getReportCount()+1);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public List<String> selectTopic() {
        return liarRepository.findDistinctAll();
    }

    @Override
    public List<String> selectKeyword(String topic) {
        Optional<List<String>> result = liarRepository.findDistinctByTopic(topic);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }
}
