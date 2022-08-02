package com.travel.travel_on.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"alarms"})
@Builder
@Table(name="user")
@Entity
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Alarm> alarms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<UserAchievement> userAchievements = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Visitation> visitations = new ArrayList<>();

}