package com.travel.travel_on.controller;

import com.travel.travel_on.auth.JwtUserDetails;
import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.dto.VideoChattingRoomDto;
import com.travel.travel_on.entity.UserVideoChattingRoom;
import com.travel.travel_on.entity.VideoChattingRoom;
import com.travel.travel_on.model.service.UserService;
import com.travel.travel_on.model.service.VideoChattingRoomService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/videochat")
@Slf4j
public class VideoChatController {

    @Autowired
    private UserService userService;

    @Autowired
    private VideoChattingRoomService videoChattingRoomService;


    @ApiOperation(value = "화상채팅방 생성")
    @Transactional
    @PostMapping("/")
    public ResponseEntity<?> regist(@ApiIgnore Authentication authentication, @RequestBody Map<String, String> param) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String roomCode = null;
            while(roomCode==null){
                roomCode=userService.getRandomString(10,false);
                if(!videoChattingRoomService.checkRoomNumber(roomCode))roomCode=null;
            }

            VideoChattingRoom newRoom = VideoChattingRoom.builder()
                    .user(userDto.toEntity())
                    .areaCode(param.get("areaCode"))
                    .privateFlag(param.get("privateFlag").equals("true"))
                    .count(Integer.parseInt(param.get("count")))
                    .roomCode(roomCode)
                    .build();
            videoChattingRoomService.insert(newRoom);

            return new ResponseEntity<String>(roomCode,HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "화상채팅방 매칭")
    @Transactional
    @PostMapping("/match")
    public ResponseEntity<?> match(@ApiIgnore Authentication authentication, @RequestBody Map<String, String> param) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String areaCode = param.get("areaName"); /******* 코드 가져오기 ******/

            VideoChattingRoom videoChattingRoom = videoChattingRoomService.match(userDto.toEntity(), areaCode);
            if(videoChattingRoom==null)  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<String>(videoChattingRoom.getRoomCode(), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

//    @ApiOperation(value = "화상채팅방 입장")
//    @GetMapping("/{room_code}")
//    public ResponseEntity<?> enter(@ApiIgnore Authentication authentication) {
//        try {
//            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
//            String userId = userDetails.getUsername();
//            UserDto userDto = userService.select(userId);
//
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            return exceptionHandling(e);
//        }
//    }

    @ApiOperation(value = "비공개 화상채팅방 입장")
    @GetMapping("/{roomCode}")
    public ResponseEntity<?> enterSecretRoom(@ApiIgnore Authentication authentication, @PathVariable String roomCode) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(videoChattingRoomService.enter(userDto.toEntity(),roomCode))  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "화상채팅방 종료")
    @DeleteMapping("/{room_code}")
    public ResponseEntity<?> close(@ApiIgnore Authentication authentication, @PathVariable String roomCode) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(videoChattingRoomService.close(userDto.toEntity(),roomCode)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }


    @ApiOperation(value = "화상채팅방 나가기")
    @GetMapping("/leave/{roomCode}")
    public ResponseEntity<?> leave(@ApiIgnore Authentication authentication, @PathVariable String roomCode) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if(videoChattingRoomService.leave(userDto.toEntity(),roomCode)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "신고하기")
    @PostMapping("/report")
    public ResponseEntity<?> report(@ApiIgnore Authentication authentication) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "라이어게임 - 주제 조회")
    @GetMapping("/liargame")
    public ResponseEntity<?> selectSubject(@ApiIgnore Authentication authentication) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "라이어게임 - 주제 선택 후 키워드 조회")
    @GetMapping("/liargame/{subject}")
    public ResponseEntity<?> selectKeyword(@ApiIgnore Authentication authentication) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
