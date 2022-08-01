package com.travel.travel_on.dto;

import com.travel.travel_on.entity.Alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDto {
    private Integer alarmId;
    private String content;

    public AlarmDto(Alarm alarm){
        alarmId=alarm.getAlarmId();
        content=alarm.getContent();
    }
}
