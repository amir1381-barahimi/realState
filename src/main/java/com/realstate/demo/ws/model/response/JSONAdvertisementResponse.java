package com.realstate.demo.ws.model.response;


import lombok.Data;


@Data
public class JSONAdvertisementResponse {

    private Long id;
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
    private String image;
    private String numberParking;
    private Long closestHomeId;

    private String latitude;
    private String longitude;
}
