package com.travel.travel_on.dto;

import com.travel.travel_on.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public User toEntity() {
        return User.builder()
                .userId(this.userId)
                .realId(this.realId)
                .nickname(this.nickname)
                .password(this.password)
                .email(this.email)
                .address(this.address)
                .adminFlag(this.adminFlag)
                .userTitle(this.userTitle)
                .reportCount(this.reportCount)
                .alarmFlag(this.alarmFlag)
                .build();
    }

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.realId = user.getRealId();
        this.nickname = user.getNickname();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.adminFlag = user.isAdminFlag();
        this.userTitle = user.getUserTitle();
        this.reportCount = user.getReportCount();
        this.alarmFlag = user.isAlarmFlag();
    }
}
