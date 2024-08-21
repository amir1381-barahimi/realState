package com.realstate.demo.ws.util;

import com.realstate.demo.ws.model.entity.Advertisement;
import com.realstate.demo.ws.model.entity.Home;
import com.realstate.demo.ws.model.enums.ListingStatus;
import com.realstate.demo.ws.model.enums.PropertyStatus;
import com.realstate.demo.ws.model.response.JSONAdvertisementResponse;
import com.realstate.demo.ws.repository.HomeRepository;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class AdUtils {
    private final HomeRepository homeRepository;

    public AdUtils(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    public Advertisement convert(String title, String description, long homeId, int contactNumber, ListingStatus listingStatus, PropertyStatus propertyStatus) {

        Home home = homeRepository.findHomeById(homeId);
        Advertisement i = new Advertisement();
        i.setContactNumber(contactNumber);
        i.setDescription(description);
        i.setHome(home);
        i.setListingStatus(listingStatus);
        i.setPropertyStatus(propertyStatus);
        i.setTitle(title);
        return i;

    }

    public JSONAdvertisementResponse convert(Advertisement i) {
        JSONAdvertisementResponse j = new JSONAdvertisementResponse();
        j.setContactNumber(i.getContactNumber());
        j.setDescription(i.getDescription());
        j.setHome(i.getHome());
        j.setTitle(i.getTitle());
        j.setContactNumber(i.getContactNumber());
        j.setPropertyStatus(i.getPropertyStatus());
        j.setListingStatus(i.getListingStatus());
        return j;
    }

    public List<JSONAdvertisementResponse> convert(List<Advertisement> list) {
        List<JSONAdvertisementResponse> jList = new ArrayList<>();
        for (Advertisement item : list) {
            JSONAdvertisementResponse j = convert(item);
            jList.add(j);
        }
        return jList;

    }
}
