package com.travel.travel_on.model.service;


import com.travel.travel_on.entity.Gugun;
import com.travel.travel_on.entity.Sido;

public interface AreaService {
    Sido selectSidoName(String splitCode);
    Gugun selectGugunName(String splitCode);
}
