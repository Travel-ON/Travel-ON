package com.travel.travel_on.dto;

import com.travel.travel_on.entity.UserAchievement;
import lombok.Data;

@Data
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
