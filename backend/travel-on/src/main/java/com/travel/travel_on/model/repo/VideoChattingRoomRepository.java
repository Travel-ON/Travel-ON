package com.travel.travel_on.model.repo;

import com.travel.travel_on.entity.User;
import com.travel.travel_on.entity.VideoChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoChattingRoomRepository extends JpaRepository<VideoChattingRoom,Integer> {
    @Query(value = "SELECT * from video_chatting_room where play_game=0 and private_flag=0 and area_code = ?1", nativeQuery = true)
    List<VideoChattingRoom> findByAreaCode(String areaCode);
    VideoChattingRoom findByRoomCode(String roomCode);
}
