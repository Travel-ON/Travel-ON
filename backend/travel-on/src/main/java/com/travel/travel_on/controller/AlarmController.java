package com.travel.travel_on.controller;

import com.travel.travel_on.auth.JwtUserDetails;
import com.travel.travel_on.dto.AlarmDto;
import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.entity.Alarm;
import com.travel.travel_on.model.service.AlarmService;
import com.travel.travel_on.model.service.UserService;

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
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/alarm")
@Slf4j
public class AlarmController {

    @Autowired
    private UserService userService;

    @Autowired
    private AlarmService alarmService;

    @ApiOperation(value = "알림 조회: 사용자가 알림 리스트를 조회한다", response = List.class)
    @GetMapping("/")
    public ResponseEntity<?> selectAlarm(@ApiIgnore Authentication authentication) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<Alarm> list = alarmService.selectAll(userDto.toEntity());
            List<AlarmDto> result = list.stream()
                    .map(r -> new AlarmDto(r))
                    .collect(Collectors.toList());
            return new ResponseEntity<List>(result, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "알림 삭제: 사용자가 알림 리스트를 전체 삭제한다")
    @DeleteMapping("/")
    @Transactional
    public ResponseEntity<?> deleteAlarm(@ApiIgnore Authentication authentication) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            alarmService.deleteAll(userDto.toEntity());
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
