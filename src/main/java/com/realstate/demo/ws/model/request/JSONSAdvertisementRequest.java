package com.realstate.demo.ws.model.request;

import com.realstate.demo.ws.model.enums.ListingStatus;
import com.realstate.demo.ws.model.enums.PropertyStatus;
import lombok.Data;


@Data
public class JSONSAdvertisementRequest {
    private String title;
    private String description;
    private int contactNumber;
    private PropertyStatus propertyStatus;
    private ListingStatus listingStatus;
    private long homeId;
}
