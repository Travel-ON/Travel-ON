package com.travel.travel_on.dto;

import com.travel.travel_on.entity.VisitPlace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisitPlaceDto {
    private Integer visitPlaceId;
    private String visitedPlace;
    private Double ratePoint;
    private String reivew;
    private String sidoName;
    private String gugunName;
    private String visitDate;

    public VisitPlaceDto(VisitPlace visitPlace){
        visitPlaceId = visitPlace.getVisitPlaceId();
        visitedPlace = visitPlace.getVisitedPlace();
        ratePoint = visitPlace.getRatePoint();
        reivew = visitPlace.getReivew();
        sidoName = visitPlace.getSidoName();
        gugunName = visitPlace.getSidoName();
        visitDate = visitPlace.getVisitDate();
    }
}
