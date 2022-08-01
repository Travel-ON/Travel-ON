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
@Table(name="user_achievement")
@Entity
public class UserAchievement implements Serializable {

    @Id
    @Column(name="user_achievement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL의 AUTO_INCREMENT를 사용
    private Integer userAchievementId;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="sido_name", length = 20, nullable = false)
    private String sidoName;

    @Column(length = 20, nullable = false)
    private String title;
}
