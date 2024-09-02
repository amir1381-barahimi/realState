package com.realstate.demo.ws.util;

import com.realstate.demo.ws.model.entity.Advertisement;
import com.realstate.demo.ws.model.entity.UserEntity;
import com.realstate.demo.ws.model.response.JSONAdvertisementResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class AdUtils {


    public Advertisement convert(String title, String description, String street, String city, String postalCode, String country, String statePrice, String stateType, String numberBath, String numberBed, String email, String phone,String image, UserEntity user) {

        Advertisement i = new Advertisement();
        i.setDescription(description);
        i.setTitle(title);
        i.setStreet(street);
        i.setCity(city);
        i.setCountry(country);
        i.setPostalCode(postalCode);
        i.setStatePrice(statePrice);
        i.setStateType(stateType);
        i.setNumberBath(numberBath);
        i.setNumberBed(numberBed);
        i.setEmail(email);
        i.setPhone(phone);
        i.setRent(false);
        i.setUser(user);
        i.setImage(image);
        return i;

    }

    public JSONAdvertisementResponse convert(Advertisement i) {
        JSONAdvertisementResponse j = new JSONAdvertisementResponse();
        j.setId(i.getId());
        j.setDescription(i.getDescription());
        j.setTitle(i.getTitle());
        j.setDescription(i.getDescription());
        j.setStreet(i.getStreet());
        j.setCity(i.getCity());
        j.setPostalCode(i.getPostalCode());
        j.setCountry(i.getCountry());
        j.setStatePrice(i.getStatePrice());
        j.setStateType(i.getStateType());
        j.setRent(false);
        j.setNumberBath(i.getNumberBath());
        j.setNumberBed(i.getNumberBed());
        j.setEmail(i.getEmail());
        j.setPhone(i.getPhone());
        j.setImage(i.getImage());
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
