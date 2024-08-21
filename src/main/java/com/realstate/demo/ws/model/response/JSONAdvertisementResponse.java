package com.realstate.demo.ws.model.response;

import com.realstate.demo.ws.model.entity.Home;
import com.realstate.demo.ws.model.entity.UserEntity;
import com.realstate.demo.ws.model.enums.ListingStatus;
import com.realstate.demo.ws.model.enums.PropertyStatus;
import lombok.Data;


@Data
public class JSONAdvertisementResponse {

    private String title;
    private String description;
    private int contactNumber;
    private UserEntity user;
    private Home home;
    private PropertyStatus propertyStatus;
    private ListingStatus listingStatus;
}
