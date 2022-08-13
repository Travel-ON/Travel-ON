package com.travel.travel_on.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterDto {
    String startDate;
    String endDate;
    String sidoName;
    String gugunName;
    String visitPlace;

}
