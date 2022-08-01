package com.travel.travel_on.controller;

import com.travel.travel_on.dto.AlarmDto;
import com.travel.travel_on.entity.Alarm;
import com.travel.travel_on.entity.User;
import com.travel.travel_on.model.service.AlarmService;
import com.travel.travel_on.model.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/alarm")
@Slf4j
public class AlarmController {

    @Autowired
    private UserService usvc;

    @Autowired
    private AlarmService asvc;

    @ApiOperation(value = "알림 조회: 사용자가 알림 리스트를 조회한다", response = List.class)
    @GetMapping("/{id}")
    public ResponseEntity<?> selectAlarm(@PathVariable String id) {
        try {
            User user = usvc.select(id);
            List<Alarm> list = asvc.selectAll(user);
            List<AlarmDto> result = list.stream()
                    .map(r -> new AlarmDto(r))
                    .collect(Collectors.toList());
            log.info("AlarmList : {}", result.toString());
            return new ResponseEntity<List>(result, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "알림 삭제: 사용자가 알림 리스트를 전체 삭제한다", response = Integer.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlarm(@PathVariable String id) {
        try {
            User user = usvc.select(id);
            int result = asvc.deleteAll(user);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
