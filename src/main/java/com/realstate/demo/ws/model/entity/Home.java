package com.realstate.demo.ws.model.entity;


import com.realstate.demo.ws.model.enums.Amenities;
import com.realstate.demo.ws.model.enums.TypeOfHome;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


import java.util.List;

@Entity
@Data
public class Home {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String price;
    private String address;
    private String city;
    private Integer size;
    private String numberOfRoom;
    private TypeOfHome type;
    private String yearBuilt;
    private Amenities amenities;
}
