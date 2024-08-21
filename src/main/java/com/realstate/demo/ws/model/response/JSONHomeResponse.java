package com.realstate.demo.ws.model.response;

import com.realstate.demo.ws.model.enums.Amenities;
import com.realstate.demo.ws.model.enums.TypeOfHome;
import lombok.Data;


import java.util.List;

@Data
public class JSONHomeResponse {
    private Long id;
    private String price;
    private String address;
    private String city;
    private int size;
    private String numberOfRoom;
    private TypeOfHome type;
    private String yearBuilt;
    private Amenities amenities;
}
