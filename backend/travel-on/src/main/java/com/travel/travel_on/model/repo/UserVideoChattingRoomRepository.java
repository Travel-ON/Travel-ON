package com.travel.travel_on.model.repo;

import com.travel.travel_on.entity.Alarm;
import com.travel.travel_on.entity.User;
import com.travel.travel_on.entity.UserVideoChattingRoom;
import com.travel.travel_on.entity.VideoChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserVideoChattingRoomRepository extends JpaRepository<UserVideoChattingRoom,Integer> {
    UserVideoChattingRoom findByUser(User user);
    List<UserVideoChattingRoom> findByVideoChattingRoom(VideoChattingRoom videoChattingRoom);
}
