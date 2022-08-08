package com.travel.travel_on.dto;

import com.travel.travel_on.entity.User;
import com.travel.travel_on.entity.UserAchievement;
import com.travel.travel_on.entity.UserVideoChattingRoom;
import com.travel.travel_on.entity.VideoChattingRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserVideoChattingRoomDto {
    private int userId;
    private int videoChattingRoomId;
    private String nickname;
    private String userTitle;

    public UserVideoChattingRoomDto(UserVideoChattingRoom userVideoChattingRoom){
        userId=userVideoChattingRoom.getUser().getUserId();
        videoChattingRoomId=userVideoChattingRoom.getVideoChattingRoom().getVideoChattingRoomId();
        nickname=userVideoChattingRoom.getNickname();
        userTitle=userVideoChattingRoom.getUserTitle();
    }
}
