package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.FilterDto;
import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.dto.VisitExpectedDto;
import com.travel.travel_on.dto.VisitPlaceDto;
import com.travel.travel_on.entity.*;
import com.travel.travel_on.model.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PlannerServiceImpl implements PlannerService{

    @Autowired
    PlaceRepositorty pRepo;

    @Autowired
    VisitPlaceRepository vRepo;

    @Autowired
    VisitExpectedRepository eRepo;

    @Autowired
    SidoRepository sRepo;

    @Autowired
    GugunRepository gRepo;

    @Autowired
    UserRepository uRepo;

    @Override
    public List<VisitPlace> selectVisitAll(User user) {
        List<VisitPlace> list = vRepo.findByUser(user);

        return list;
    }

    @Override
    public List<VisitPlace> selectVisitFilter(User user, FilterDto filterDto) {
        List<VisitPlace> list;
        List<VisitPlace> vlist = new LinkedList<>();

        if(filterDto.getVisitPlace() == null && filterDto.getGugunName() == null && filterDto.getSidoName() == null){
            list = vRepo.findByUserAndVisitDateBetween(user, filterDto.getStartDate(), filterDto.getEndDate());

            return list;
        }else if(filterDto.getVisitPlace() == null && filterDto.getGugunName() == null){
            list = vRepo.findByUserAndVisitDateBetween(user, filterDto.getStartDate(), filterDto.getEndDate());

            for(VisitPlace visitPlace : list){
                if(visitPlace.getSidoName().contains(filterDto.getSidoName())){
                    vlist.add(visitPlace);
                }
            }
            return vlist;
        }else if(filterDto.getVisitPlace() == null){
            list = vRepo.findByUserAndVisitDateBetween(user, filterDto.getStartDate(), filterDto.getEndDate());

            for(VisitPlace visitPlace : list){
                if(visitPlace.getGugunName().contains(filterDto.getGugunName())
                        && visitPlace.getSidoName().contains(filterDto.getSidoName())){
                    vlist.add(visitPlace);
                }
            }
            return vlist;
        }else{
            list = vRepo.findByUserAndVisitDateBetween(user, filterDto.getStartDate(), filterDto.getEndDate());

            for(VisitPlace visitPlace : list){
                if(visitPlace.getVisitedPlace().contains(filterDto.getVisitPlace())
                        && visitPlace.getGugunName().contains(filterDto.getGugunName())
                        && visitPlace.getSidoName().contains(filterDto.getSidoName())){
                    vlist.add(visitPlace);
                }
            }
            return vlist;
        }

    }

    @Override
    public List<VisitPlace> selectModalVisitAll(String nickName) {
        Optional<User> oppUser = uRepo.findByNickname(nickName);
        if(oppUser.isPresent()){
            User user = oppUser.get();
            List<VisitPlace> list = vRepo.findByUser(user);
            return list;
        }
            return null;
    }

    @Override
    public List<VisitExpected> selectExpectedAll(User user) {
        List<VisitExpected> list = eRepo.findByUser(user);

        return list;
    }

    @Override
    public List<VisitExpected> selectExpectedFilter(User user, FilterDto filterDto) {
        List<VisitExpected> list;
        List<VisitExpected> vlist = new LinkedList<>();

        if(filterDto.getVisitPlace() == null && filterDto.getGugunName() == null && filterDto.getSidoName() == null){
            list = eRepo.findByUserAndExpectedDateBetween(user, filterDto.getStartDate(), filterDto.getEndDate());

            return list;
        }else if(filterDto.getVisitPlace() == null && filterDto.getGugunName() == null){
            list = eRepo.findByUserAndExpectedDateBetween(user, filterDto.getStartDate(), filterDto.getEndDate());

            for(VisitExpected visitExpected : list){
                if(visitExpected.getSidoName().contains(filterDto.getSidoName())){
                    vlist.add(visitExpected);
                }
            }
            return vlist;
        }else if(filterDto.getVisitPlace() == null){
            list = eRepo.findByUserAndExpectedDateBetween(user, filterDto.getStartDate(), filterDto.getEndDate());

            for(VisitExpected visitExpected : list){
                if(visitExpected.getGugunName().contains(filterDto.getGugunName())
                        && visitExpected.getSidoName().contains(filterDto.getSidoName())){
                    vlist.add(visitExpected);
                }
            }
            return vlist;
        }else{
            list = eRepo.findByUserAndExpectedDateBetween(user, filterDto.getStartDate(), filterDto.getEndDate());

            for(VisitExpected visitExpected : list){
                if(visitExpected.getExpectedPlace().contains(filterDto.getVisitPlace())
                        && visitExpected.getGugunName().contains(filterDto.getGugunName())
                        && visitExpected.getSidoName().contains(filterDto.getSidoName())){
                    vlist.add(visitExpected);
                }
            }
            return vlist;
        }
    }

    @Override
    public List<VisitExpected> selectModalExpectedAll(String nickName) {
        Optional<User> oppUser = uRepo.findByNickname(nickName);
        if(oppUser.isPresent()){
            User user = oppUser.get();
            List<VisitExpected> list = eRepo.findByUser(user);
            return list;
        }
        return null;
    }

    @Override
    public VisitPlaceDto selectVisitOne(Integer id) {
        Optional<VisitPlace> result = vRepo.findById(id);
        if(result.isPresent()){
            VisitPlace visitPlace = result.get();
            VisitPlaceDto visitPlaceDto = new VisitPlaceDto(visitPlace);
            return visitPlaceDto;
        }

        return null;
    }

    @Override
    public VisitExpectedDto selectExpectedOne(Integer id) {
        Optional<VisitExpected> result = eRepo.findById(id);
        if(result.isPresent()){
            VisitExpected visitExpected = result.get();
            VisitExpectedDto visitExpectedDto = new VisitExpectedDto(visitExpected);
            return visitExpectedDto;
        }

        return null;
    }

    @Override
    public boolean writeVisit(UserDto userDto, VisitPlaceDto visitPlaceDto) {
        User user = userDto.toEntity();
        if(visitPlaceDto == null){
            return false;
        }
        VisitPlace visitPlace = VisitPlace.builder()
                .user(user)
                .visitedPlace(visitPlaceDto.getVisitedPlace())
                .ratePoint(visitPlaceDto.getRatePoint())
                .review(visitPlaceDto.getReview())
                .sidoName(visitPlaceDto.getSidoName())
                .gugunName(visitPlaceDto.getGugunName())
                .visitDate(visitPlaceDto.getVisitDate())
                .build();
        visitPlace.setUser(user);
        vRepo.save(visitPlace);
        return true;
    }

    @Override
    public boolean writeExpected(UserDto userDto, VisitExpectedDto visitExpectedDto) {
        User user = userDto.toEntity();
        if(visitExpectedDto == null){
            return false;
        }
        VisitExpected visitExpected = VisitExpected.builder()
                .user(user)
                .expectedPlace(visitExpectedDto.getExpectedPlace())
                .sidoName(visitExpectedDto.getSidoName())
                .gugunName(visitExpectedDto.getGugunName())
                .expectedDate(visitExpectedDto.getExpectedDate())
                .build();
        visitExpected.setUser(user);
        eRepo.save(visitExpected);
        return true;
    }

    @Override
    public boolean writePlace(Place place) {
        if(place == null) return false;
        Optional<Place> result = pRepo.findFirstByVisitPlaceContaining(place.getVisitPlace());
        if(result.isPresent()){
            return true;
        }else{
            pRepo.save(place);
            return true;
        }
    }

    @Override
    public boolean updateVisit(VisitPlaceDto visitPlaceDto) {
        Optional<VisitPlace> result = vRepo.findById(visitPlaceDto.getVisitPlaceId());

        if(result.isPresent()){
            VisitPlace visitPlace = result.get();
            visitPlace.setVisitedPlace(visitPlaceDto.getVisitedPlace());
            visitPlace.setRatePoint(visitPlaceDto.getRatePoint());
            visitPlace.setReview(visitPlaceDto.getReview());
            visitPlace.setSidoName(visitPlaceDto.getSidoName());
            visitPlace.setGugunName(visitPlaceDto.getGugunName());
            visitPlace.setVisitDate(visitPlaceDto.getVisitDate());
            vRepo.save(visitPlace);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public boolean deleteVisit(Integer id) {
        Optional<VisitPlace> result = vRepo.findById(id);

        if(result == null){
            return false;
        }else{
            result.ifPresent(visitPlace -> {
                vRepo.delete(visitPlace);
            });
            return true;
        }
    }

    @Override
    public boolean deleteExpected(Integer id) {
        Optional<VisitExpected> result = eRepo.findById(id);
        if(result == null){
            return false;
        }else{
            result.ifPresent(visitExpected -> {
                eRepo.delete(visitExpected);
            });
            return true;
        }
    }

    @Override
    public List<Place> autoKeyward(String keyword) {

        List<Place> list = pRepo.findByVisitPlaceContaining(keyword);
        List<Place> pList = new ArrayList<>();

        int cnt = 0;
        for(Place place : list){

            pList.add(place);
            if(cnt == 4) break;
            cnt++;
        }
        return pList;
    }

    @Override
    public List<Gugun> loadGugun(String sidoName) {
        Optional<Sido> value = sRepo.findFirstBySidoNameContaining(sidoName);
        if(value.isPresent()){
            Sido sido = value.get();
            String splitCode = sido.getSidoCode().charAt(0) + "" + sido.getSidoCode().charAt(1);
            System.out.println(splitCode);
            return gRepo.findByGugunCodeStartsWith(splitCode);
        }else return null;
    }


}
