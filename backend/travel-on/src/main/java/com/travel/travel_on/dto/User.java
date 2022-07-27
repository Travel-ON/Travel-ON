package com.travel.travel_on.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name="user")
@Entity
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL의 AUTO_INCREMENT를 사용
    private Integer userId;

    @Column(name="id", length = 20,nullable = false, unique = true)
    private String realId;

    @Column(length = 20, nullable = false, unique = true)
    private String nickname;

    @Column(length = 20, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 150, nullable = false)
    private String address;

    @Column(name = "admin_flag",nullable = false)
    private boolean adminFlag;

    @Column(name="user_title",length = 20)
    private String userTitle;

    @Column(name="report_count",nullable = false)
    private int reportCount;

    @Column(name="alarm_flag", nullable = false)
    private boolean alarmFlag;
}
