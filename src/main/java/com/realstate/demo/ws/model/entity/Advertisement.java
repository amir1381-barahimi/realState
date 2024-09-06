package com.realstate.demo.ws.model.entity;

import com.realstate.demo.ws.model.enums.ListingStatus;
import com.realstate.demo.ws.model.enums.PropertyStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
    @Lob
    private String image;
    private String numberParking;
    @ManyToOne
    private UserEntity user;
}
 