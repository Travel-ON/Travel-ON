package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.FilterDto;
import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.dto.VisitExpectedDto;
import com.travel.travel_on.dto.VisitPlaceDto;
import com.travel.travel_on.entity.*;

import java.util.List;


public interface PlannerService {

    List<VisitPlace> selectVisitAll(User user);

    List<VisitPlace> selectVisitFilter(User user, FilterDto filterDto);

    List<VisitPlace> selectModalVisitAll(String nickName);

    List<VisitExpected> selectExpectedAll(User user);

    List<VisitExpected> selectExpectedFilter(User user, FilterDto filterDto);

    List<VisitExpected> selectModalExpectedAll(String nickName);

    VisitPlaceDto selectVisitOne(Integer id);

    VisitExpectedDto selectExpectedOne(Integer id);

    boolean writeVisit(UserDto userDto, VisitPlaceDto visitPlaceDto);

    boolean writeExpected(UserDto userDto, VisitExpectedDto visitExpectedDto);

    boolean writePlace(Place place);

    boolean updateVisit(VisitPlaceDto visitPlaceDto);

    boolean deleteVisit(Integer id);

    boolean deleteExpected(Integer id);

    List<Place> autoKeyward(String keyword);

    List<Gugun> loadGugun(String sidoName);
}
