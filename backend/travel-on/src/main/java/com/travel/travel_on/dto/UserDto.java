package com.travel.travel_on.dto;

import com.travel.travel_on.entity.User;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private Integer userId;
    private String id;
    private String nickname;
    private String password;
    private String email;
    private String address;
    private boolean adminFlag;
    private String userTitle;
    private int reportCount;
    private boolean alarmFlag;

    UserDto(String id,String nickname,String password,String email,String address ){
        this.id=id;
        this.nickname=nickname;
        this.password=password;
        this.email=email;
        this.address=address;
    }

    public User toEntity() {
        return User.builder()
                .userId(this.userId)
                .realId(this.id)
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
        this.id = user.getRealId();
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
