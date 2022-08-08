package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.dto.VisitExpectedDto;
import com.travel.travel_on.dto.VisitPlaceDto;
import com.travel.travel_on.entity.Place;
import com.travel.travel_on.entity.User;
import com.travel.travel_on.entity.VisitExpected;
import com.travel.travel_on.entity.VisitPlace;

import java.util.List;


public interface PlannerService {

    List<VisitPlace> selectVisitAll(User user);

    List<VisitExpected> selectExpectedAll(User user);

    VisitPlaceDto selectVisitOne(Integer id);

    VisitExpectedDto selectExpectedOne(Integer id);

    boolean writeVisit(UserDto userDto, VisitPlaceDto visitPlaceDto);

    boolean writeExpected(UserDto userDto, VisitExpectedDto visitExpectedDto);

    boolean writePlace(Place place);

    boolean updateVisit(VisitPlaceDto visitPlaceDto);

    boolean deleteVisit(Integer id);

    boolean deleteExpected(Integer id);

    List<Place> autoKeyward(String keyword);
}
