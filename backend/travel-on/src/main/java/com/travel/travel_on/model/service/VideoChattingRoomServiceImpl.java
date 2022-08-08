package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.dto.VideoChattingRoomDto;
import com.travel.travel_on.entity.User;
import com.travel.travel_on.entity.UserVideoChattingRoom;
import com.travel.travel_on.entity.VideoChattingRoom;
import com.travel.travel_on.model.repo.UserRepository;
import com.travel.travel_on.model.repo.UserVideoChattingRoomRepository;
import com.travel.travel_on.model.repo.VideoChattingRoomRepository;
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

        UserVideoChattingRoom userVideoChattingRoom = UserVideoChattingRoom.builder()
                .userId(user.getUserId())
                .videoChattingRoom(result)
                .nickname(user.getNickname())
                .userTitle(user.getUserTitle())
                .build();
        userVideoChattingRoomRepository.save(userVideoChattingRoom);
        return true;
    }

    @Override
    public VideoChattingRoom match(User user, String areaCode) {

        if(userVideoChattingRoomRepository.findByUser(user)!=null) {
            return null;
        }

        List<VideoChattingRoom> videoChattingRooms = videoChattingRoomRepository.findByAreaCode(areaCode);
        VideoChattingRoom videoChattingRoom = null;
        while(videoChattingRooms!=null && videoChattingRoom==null){
            int index = ThreadLocalRandom.current().nextInt(0, videoChattingRooms.size());
            videoChattingRoom = videoChattingRooms.get(index);
            if(videoChattingRoom.getCount()==userVideoChattingRoomRepository.findByVideoChattingRoom(videoChattingRoom).size()){
                videoChattingRooms.remove(index);
                videoChattingRoom=null;
            }
        }
        if(videoChattingRoom!=null){
            UserVideoChattingRoom userVideoChattingRoom = UserVideoChattingRoom.builder()
                    .userId(user.getUserId())
                    .videoChattingRoom(videoChattingRoom)
                    .nickname(user.getNickname())
                    .userTitle(user.getUserTitle())
                    .build();
            userVideoChattingRoomRepository.save(userVideoChattingRoom);
        }
        return videoChattingRoom;
    }

    @Override
    public boolean enter(User user, String roomCode) {
        if(userVideoChattingRoomRepository.findByUser(user)!=null) {
            return false;
        }
        VideoChattingRoom videoChattingRoom = videoChattingRoomRepository.findByRoomCode(roomCode);
        if(videoChattingRoom==null) {
            return false;
        }
        if(videoChattingRoom.getCount()==userVideoChattingRoomRepository.findByVideoChattingRoom(videoChattingRoom).size()){
            return false;
        }
        UserVideoChattingRoom userVideoChattingRoom = UserVideoChattingRoom.builder()
                .userId(user.getUserId())
                .videoChattingRoom(videoChattingRoom)
                .nickname(user.getNickname())
                .userTitle(user.getUserTitle())
                .build();
        userVideoChattingRoomRepository.save(userVideoChattingRoom);
        return true;
    }

    @Override
    public boolean leave(User user, String roomCode) {
        VideoChattingRoom videoChattingRoom = videoChattingRoomRepository.findByRoomCode(roomCode);
        if(videoChattingRoom==null) {
            return false;
        }

        UserVideoChattingRoom userVideoChattingRoom = userVideoChattingRoomRepository.findByUser(user);
        if(userVideoChattingRoomRepository.findByUser(user)==null ||
                !userVideoChattingRoom.getVideoChattingRoom().getRoomCode().equals(roomCode)) {
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
                videoChattingRoom.getUser()!=user) {
            return false;
        }

        userVideoChattingRoomRepository.delete(userVideoChattingRoom);
        return true;
    }
}
