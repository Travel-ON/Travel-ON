package com.travel.travel_on.dto;

import com.travel.travel_on.entity.Visitation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
