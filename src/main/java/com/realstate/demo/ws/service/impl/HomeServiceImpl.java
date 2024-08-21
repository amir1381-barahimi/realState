package com.realstate.demo.ws.service.impl;

import com.realstate.demo.ws.model.entity.Home;
import com.realstate.demo.ws.model.enums.Amenities;
import com.realstate.demo.ws.model.enums.TypeOfHome;
import com.realstate.demo.ws.model.response.JSONHomeResponse;
import com.realstate.demo.ws.repository.HomeRepository;
import com.realstate.demo.ws.service.HomeService;
import com.realstate.demo.ws.util.HomeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    private final HomeUtils utils;

    private final HomeRepository homeRepository;
    public HomeServiceImpl(HomeUtils utils, HomeRepository homeRepository) {
        this.utils = utils;
        this.homeRepository = homeRepository;
    }

    @Override
    public JSONHomeResponse createHome(String address, String city, Amenities amenities, String price, int size, TypeOfHome type, String numberOfRoom, String yearBuilt) {
        Home i = utils.convert(address, city, amenities, price, size, type, numberOfRoom, yearBuilt);
        homeRepository.save(i);
        return utils.convert(i);
    }

    @Override
    public List<JSONHomeResponse> getAllHome() {
        List<Home> list = (List<Home>) homeRepository.findAll();
        return utils.convert(list);
    }

    @Override
    public JSONHomeResponse getHome(long id) {
        Home i = homeRepository.findHomeById(id);
        //        Home i = homeRepository.findHomeById(id);
        return utils.convert(i);
    }

    @Override
    public ResponseEntity deleteHome(long id) {
        homeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
