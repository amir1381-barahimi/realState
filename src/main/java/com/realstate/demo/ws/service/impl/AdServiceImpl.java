package com.realstate.demo.ws.service.impl;

import com.realstate.demo.ws.model.entity.Advertisement;
import com.realstate.demo.ws.model.entity.UserEntity;
import com.realstate.demo.ws.model.enums.ListingStatus;
import com.realstate.demo.ws.model.enums.PropertyStatus;
import com.realstate.demo.ws.model.response.JSONAdvertisementResponse;
import com.realstate.demo.ws.repository.AdRepository;
import com.realstate.demo.ws.service.AdService;
import com.realstate.demo.ws.util.AdUtils;
import com.realstate.demo.ws.util.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AdServiceImpl implements AdService {

    private final AdUtils utils;
    private final UserUtils userUtils;
    private final AdRepository adRepository;

    public AdServiceImpl(AdUtils utils, UserUtils userUtils, AdRepository adRepository) {
        this.utils = utils;
        this.userUtils = userUtils;
        this.adRepository = adRepository;
    }

    @Override
    public JSONAdvertisementResponse createAd(String title, String description, long homeId, int contactNumber, ListingStatus listingStatus, PropertyStatus propertyStatus, HttpServletRequest request) {
        UserEntity user = userUtils.getCurrentUser(request);
        Advertisement i = utils.convert(title, description, homeId, contactNumber, listingStatus, propertyStatus);
        i.setUser(user);
        adRepository.save(i);
        return utils.convert(i);
    }

    @Override
    public JSONAdvertisementResponse getAd(long id) {
        Advertisement i = adRepository.findAdById(id);
        return utils.convert(i);
    }

    @Override
    public List<JSONAdvertisementResponse> getAllAdd() {
        List<Advertisement> list = adRepository.findAll();
        return utils.convert(list);
    }

    @Override
    public void deleteAd(long id) {
        adRepository.deleteById(id);
    }
}
