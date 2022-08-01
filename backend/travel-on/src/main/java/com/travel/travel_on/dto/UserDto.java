package com.travel.travel_on.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer userId;
    private String realId;
    private String nickname;
    private String password;
    private String email;
    private String address;
    private boolean adminFlag;
    private String userTitle;
    private int reportCount;
    private boolean alarmFlag;
}
