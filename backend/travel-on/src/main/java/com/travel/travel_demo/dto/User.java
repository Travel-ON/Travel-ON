package com.travel.travel_demo.dto;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table (name="user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Integer user_id;

    @Column(length = 20, nullable = false, unique = true)
    private String id;

    @Column(length = 20, nullable = false, unique = true)
    private String nickname;

    @Column(length = 20, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 150, nullable = false)
    private String address;

    @Column(nullable = false)
    private boolean admin_flag;

    @Column(length = 20)
    private String user_title;

    @Column(nullable = false)
    private int report_count;

    @Column(nullable = false)
    private boolean alarm_flag;
}
