package com.travel.travel_on.model.service;

import com.travel.travel_on.entity.Gugun;
import com.travel.travel_on.entity.Sido;
import com.travel.travel_on.model.repo.GugunRepository;
import com.travel.travel_on.model.repo.SidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    SidoRepository sidoRepository;

    @Autowired
    GugunRepository gugunRepository;

    @Override
    public Sido selectSidoName(String splitCode) {
        List<Sido> result = sidoRepository.findBySidoCodeStartsWith(splitCode);
        if(result==null || result.size()==0) return null;
        return result.get(0);
    }

    @Override
    public Gugun selectGugunName(String splitCode) {
        List<Gugun> result = gugunRepository.findByGugunCodeStartsWith(splitCode);
        if(result==null || result.size()==0) return null;
        return result.get(0);
    }
}
