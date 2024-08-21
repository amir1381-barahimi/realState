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
    private int contactNumber;
    @OneToOne
    private Home home;
    @ManyToOne
    private UserEntity user;
    private PropertyStatus propertyStatus;
    private ListingStatus listingStatus;
}
