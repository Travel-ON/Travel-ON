package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.dto.VideoChattingRoomDto;
import com.travel.travel_on.entity.User;
import com.travel.travel_on.entity.UserAchievement;
import com.travel.travel_on.entity.VideoChattingRoom;
import com.travel.travel_on.entity.Visitation;

import java.util.List;

public interface VideoChattingRoomService {
    boolean checkRoomNumber(String roomCode);
    //방 생성
    boolean insert(VideoChattingRoom videoChattingRoom);

    //방 매칭
    VideoChattingRoom match(User user, String areaCode);

    boolean enter(User user, String roomCode);

    // 방 나가기
    boolean leave(User user, String roomCode);

    //방 종료
    boolean close(User user, String roomCode);

}
