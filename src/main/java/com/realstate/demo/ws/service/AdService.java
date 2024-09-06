package com.realstate.demo.ws.service;

import com.realstate.demo.ws.model.entity.Advertisement;
import com.realstate.demo.ws.model.enums.ListingStatus;
import com.realstate.demo.ws.model.enums.PropertyStatus;
import com.realstate.demo.ws.model.response.JSONAdvertisementResponse;
import jakarta.servlet.http.HttpServletRequest;


import java.util.List;

public interface AdService {
    JSONAdvertisementResponse createAd(String title, String description, String street, String city, String postalCode, String country, String statePrice, String stateType, String numberBath, String numberBed, String email, String phone,String image,String numberParking, HttpServletRequest request);

    JSONAdvertisementResponse getAd(long id);

    List<JSONAdvertisementResponse> getAllAdd(String city);

    void deleteAd(long id);

    List<JSONAdvertisementResponse> getAllAdByUser(HttpServletRequest request);

    Advertisement findClosestHome(Long id);


}
