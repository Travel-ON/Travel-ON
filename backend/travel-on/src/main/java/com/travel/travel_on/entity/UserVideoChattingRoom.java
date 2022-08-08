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
    @Column(name = "user_id", nullable = false)
    private int userId;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="video_chatting_room_id")
    private VideoChattingRoom videoChattingRoom;

    @Column(length = 20, nullable = false)
    private String nickname;

    @Column(length = 20)
    private String userTitle;

    public void setUser(User user){
        this.user = user;
    }

    public void setVideoChattingRoom(VideoChattingRoom videoChattingRoom){
        this.videoChattingRoom = videoChattingRoom;
        this.videoChattingRoom.getUserVideoChattingRooms().add(this);
    }
}
