package com.realstate.demo.ws.service;

import com.realstate.demo.ws.model.enums.ListingStatus;
import com.realstate.demo.ws.model.enums.PropertyStatus;
import com.realstate.demo.ws.model.response.JSONAdvertisementResponse;
import jakarta.servlet.http.HttpServletRequest;


import java.util.List;

public interface AdService {
    JSONAdvertisementResponse createAd(String title, String description, long homeId, int contactNumber, ListingStatus listingStatus, PropertyStatus propertyStatus, HttpServletRequest request);

    JSONAdvertisementResponse getAd(long id);

    List<JSONAdvertisementResponse> getAllAdd();

    void deleteAd(long id);
}
