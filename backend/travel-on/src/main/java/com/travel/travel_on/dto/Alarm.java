package com.travel.travel_on.dto;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name="alarm")
@Entity
public class Alarm implements Serializable {

    @Id
    @Column(name="alarm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL의 AUTO_INCREMENT를 사용
    private Integer alarmId;

    @Column(name="user_id")
    private Integer userId;

    @Column(length = 100, nullable = false)
    private String content;
}
