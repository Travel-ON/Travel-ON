package com.travel.travel_on.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name="video_chatting_room")
@Entity
public class VideoChattingRoom implements Serializable {
    @Id
    @Column(name="video_chatting_room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer videoChattingRoomId;

    @OneToOne
    @JoinColumn(name="owner_id")
    private User user;

    @Column(name="area_code",length = 10, nullable = false)
    private String areaCode;

    @Column(name="private_flag")
    private boolean privateFlag;

    @Column
    private int count;

    @Column(name="room_code",length = 10, nullable = false)
    private String roomCode;

    @OneToMany(mappedBy = "videoChattingRoom", cascade = CascadeType.ALL)
    @Builder.Default
    private List<UserVideoChattingRoom> userVideoChattingRooms = new ArrayList<>();

    public void setUser(User user){
        this.user = user;
    }

}
