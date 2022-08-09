package com.travel.travel_on.dto;

import com.travel.travel_on.entity.User;
import com.travel.travel_on.entity.UserVideoChattingRoom;
import com.travel.travel_on.entity.VideoChattingRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoChattingRoomDto {
    private int videoChattingRoomId;
    private int ownerId;
    private String areaCode;
    private boolean privateFlag;
    private int count;
    private String roomCode;

    public VideoChattingRoomDto(VideoChattingRoom videoChattingRoom){
        this.videoChattingRoomId=videoChattingRoom.getVideoChattingRoomId();
        this.ownerId=videoChattingRoom.getUser().getUserId();
        this.areaCode=videoChattingRoom.getAreaCode();
        this.privateFlag=videoChattingRoom.isPrivateFlag();
        this.count=videoChattingRoom.getCount();
        this.roomCode=videoChattingRoom.getRoomCode();
    }

//    public VideoChattingRoomDto toEntity() {
//        return VideoChattingRoom.builder()
//                .videoChattingRoomId(this.videoChattingRoomId)
//                .user(this.nickname)
//                .areaCode(this.areaCode)
//                .privateFlag(this.privateFlag)
//                .password(this.password)
//                .count(this.count)
//                .roomCode(this.roomCode)
//                .build();
//    }
}
