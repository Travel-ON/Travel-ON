package com.travel.travel_on.controller;

import com.travel.travel_on.auth.JwtUserDetails;
import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.entity.Report;
import com.travel.travel_on.entity.VideoChattingRoom;
import com.travel.travel_on.model.service.AreaService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/videochat")
@Slf4j
public class VideoChatController {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private UserService userService;

    @Autowired
    private VideoChattingRoomService videoChattingRoomService;

    @Autowired
    private AreaService areaService;


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

            String dongCode = param.get("dongCode");
            String areaScope = param.get("areaScope");
            String areaCode;
            if(areaScope.equals("sido")){
                areaCode=areaService.selectSidoName(dongCode.substring(0,2)).getSidoCode();
            }else if(areaScope.equals("gugun")){
                areaCode=areaService.selectGugunName(dongCode.substring(0,2)).getGugunCode();
            }else if(areaScope.equals("dong")){
                areaCode=dongCode;
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            VideoChattingRoom newRoom = VideoChattingRoom.builder()
                    .user(userDto.toEntity())
                    .areaCode(areaCode)
                    .privateFlag(param.get("privateFlag").equals("true"))
                    .count(Integer.parseInt(param.get("count")))
                    .roomCode(roomCode)
                    .build();
            if(videoChattingRoomService.insert(newRoom)) {
                Map<String,String> result = new HashMap<>();
                result.put("roomCode",roomCode);
                result.put("hostName",userDto.getNickname());
                result.put("playGame","false");
                return new ResponseEntity<Map>(result,HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
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

            String dongCode = param.get("dongCode");
            String areaScope = param.get("areaScope");
            String areaCode;
            if(areaScope.equals("sido")){
                areaCode=areaService.selectSidoName(dongCode.substring(0,2)).getSidoCode();
            }else if(areaScope.equals("gugun")){
                areaCode=areaService.selectGugunName(dongCode.substring(0,2)).getGugunCode();
            }else if(areaScope.equals("dong")){
                areaCode=dongCode;
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            VideoChattingRoom videoChattingRoom = videoChattingRoomService.match(userDto.toEntity(), areaCode);
            if(videoChattingRoom==null)  {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            Map<String,String> result = new HashMap<>();
            result.put("roomCode",videoChattingRoom.getRoomCode());
            result.put("hostName",videoChattingRoom.getUser().getNickname());
            result.put("playGame",videoChattingRoom.isPlayGame()?"true":"false");
            return new ResponseEntity<Map>(result,HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "공유코드로 화상채팅방 입장")
    @Transactional
    @GetMapping("/{roomCode}")
    public ResponseEntity<?> enterSecretRoom(@ApiIgnore Authentication authentication, @PathVariable String roomCode) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            VideoChattingRoom videoChattingRoom = videoChattingRoomService.enter(userDto.toEntity(),roomCode);
            if(videoChattingRoom==null){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
                Map<String,String> result = new HashMap<>();
                result.put("roomCode",videoChattingRoom.getRoomCode());
                result.put("hostName",videoChattingRoom.getUser().getNickname());
                result.put("playGame",videoChattingRoom.isPlayGame()?"true":"false");
                return new ResponseEntity<Map>(result,HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "화상채팅방 종료")
    @Transactional
    @DeleteMapping("/{roomCode}")
    public ResponseEntity<?> close(@ApiIgnore Authentication authentication, @PathVariable String roomCode) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(videoChattingRoomService.close(userDto.toEntity(),roomCode)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }


    @ApiOperation(value = "화상채팅방 나가기")
    @Transactional
    @GetMapping("/leave/{roomCode}")
    public ResponseEntity<?> leave(@ApiIgnore Authentication authentication, @PathVariable String roomCode) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if(videoChattingRoomService.leave(userDto.toEntity(),roomCode)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "신고하기")
    @Transactional
    @PostMapping("/report")
    public ResponseEntity<?> report(@ApiIgnore Authentication authentication, @RequestBody Map<String, String> param) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null||userService.select(param.get("reportedId"))==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Date time = new Date();
            String nowTime = simpleDateFormat.format(time);
            Report report = Report.builder()
                    .reportingId(userId)
                    .reportedId(param.get("reportedId"))
                    .reportDate(nowTime)
                    .reportContent(param.get("reportContent"))
                    .build();
            if(videoChattingRoomService.report(report)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
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
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<List>(videoChattingRoomService.selectTopic(),HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "라이어게임 - 주제 선택 후 키워드 조회")
    @GetMapping("/liargame/{topic}")
    public ResponseEntity<?> selectKeyword(@ApiIgnore Authentication authentication, @PathVariable String topic) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<String> result = videoChattingRoomService.selectKeyword(topic);
            if(result==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            int index = ThreadLocalRandom.current().nextInt(0, result.size());

            return new ResponseEntity<String>(result.get(index),HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }


    @ApiOperation(value = "게임 시작")
    @Transactional
    @PutMapping("/game/{roomCode}")
    public ResponseEntity<?> startGame(@ApiIgnore Authentication authentication, @PathVariable String roomCode) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            videoChattingRoomService.update(roomCode,true);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "게임 종료")
    @Transactional
    @DeleteMapping("/game/{roomCode}")
    public ResponseEntity<?> stopGame(@ApiIgnore Authentication authentication, @PathVariable String roomCode) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            videoChattingRoomService.update(roomCode,false);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
