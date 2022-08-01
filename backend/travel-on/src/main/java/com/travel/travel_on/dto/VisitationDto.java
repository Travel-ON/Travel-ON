package com.travel.travel_on.dto;

import com.travel.travel_on.entity.Visitation;
import lombok.Data;

@Data
public class VisitationDto {
    private Integer visitationId;
    private String sidoName;
    private int count;

    public VisitationDto(Visitation visitation){
        visitationId=visitation.getVisitationId();
        sidoName=visitation.getSidoName();
        count=visitation.getCount();
    }
}
