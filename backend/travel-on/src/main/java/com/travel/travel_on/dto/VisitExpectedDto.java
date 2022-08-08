package com.travel.travel_on.dto;

import com.travel.travel_on.entity.VisitExpected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisitExpectedDto {
    private Integer visitExpectedId;
    private String expectedPlace;
    private String sidoName;
    private String gugunName;
    private String expectedDate;

    public VisitExpectedDto(VisitExpected visitExpected){
        visitExpectedId = visitExpected.getVisitExpectedId();
        expectedPlace = visitExpected.getExpectedPlace();
        sidoName = visitExpected.getSidoName();
        gugunName = visitExpected.getSidoName();
        expectedDate = visitExpected.getExpectedDate();
    }
}
