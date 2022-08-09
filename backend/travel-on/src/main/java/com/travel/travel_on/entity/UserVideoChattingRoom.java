package com.travel.travel_on.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name="user_video_chatting_room")
@Entity
public class UserVideoChattingRoom implements Serializable {
    @Id
    @Column(name="user_video_chatting_room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer UserVideoChattingRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="video_chatting_room_id")
    private VideoChattingRoom videoChattingRoom;

    public void setUser(User user){
        this.user = user;
        this.user.getUserVideoChattingRooms().add(this);
    }

    public void setVideoChattingRoom(VideoChattingRoom videoChattingRoom){
        this.videoChattingRoom = videoChattingRoom;
        this.videoChattingRoom.getUserVideoChattingRooms().add(this);
    }
}
