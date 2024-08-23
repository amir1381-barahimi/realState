package com.realstate.demo.ws.model.request;

import com.realstate.demo.ws.model.enums.ListingStatus;
import com.realstate.demo.ws.model.enums.PropertyStatus;
import lombok.Data;


@Data
public class JSONSAdvertisementRequest {
    private String title;
    private String description;
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private String statePrice;
    private String stateType;
    private boolean rent;
    private String numberBath;
    private String numberBed;
    private String email;
    private String phone;
}
