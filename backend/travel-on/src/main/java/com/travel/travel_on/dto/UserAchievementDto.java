package com.travel.travel_on.dto;

import com.travel.travel_on.entity.UserAchievement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAchievementDto {
    private Integer userAchievementId;
    private String sidoName;
    private String title;

    public UserAchievementDto(UserAchievement userAchievement){
        userAchievementId=userAchievement.getUserAchievementId();
        sidoName=userAchievement.getSidoName();
        title=userAchievement.getTitle();
    }
}
