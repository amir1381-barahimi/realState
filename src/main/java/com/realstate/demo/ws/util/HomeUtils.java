//package com.realstate.demo.ws.util;
//
//import com.realstate.demo.ws.model.entity.Home;
//import com.realstate.demo.ws.model.enums.Amenities;
//import com.realstate.demo.ws.model.enums.TypeOfHome;
//import com.realstate.demo.ws.model.response.JSONHomeResponse;
//import org.springframework.stereotype.Component;
//
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class HomeUtils {
//
//    public Home convert(String address, String city, Amenities amenities, String price, int size, TypeOfHome type, String numberOfRoom, String yearBuilt) {
//        Home i = new Home();
//        i.setAddress(address);
//        i.setCity(city);
//        i.setAmenities(amenities);
//        i.setPrice(price);
//        i.setSize(size);
//        i.setType(type);
//        i.setNumberOfRoom(numberOfRoom);
//        i.setYearBuilt(yearBuilt);
//
//        return i;
//    }
//
//    public JSONHomeResponse convert(Home i) {
//        JSONHomeResponse j = new JSONHomeResponse();
//        j.setId(i.getId());
//        j.setAddress(i.getAddress());
//        j.setAmenities(i.getAmenities());
//        j.setCity(i.getCity());
//        j.setType(i.getType());
//        j.setPrice(i.getPrice());
//        j.setSize(i.getSize());
//        j.setNumberOfRoom(i.getNumberOfRoom());
//        j.setYearBuilt(i.getYearBuilt());
//        return j;
//    }
//
//    public List<JSONHomeResponse> convert(List<Home> homes) {
//        List<JSONHomeResponse> jList = new ArrayList<>();
//        if (homes != null) {
//            for (Home item : homes) {
//                JSONHomeResponse j = convert(item);
//                jList.add(j);
//            }
//        }
//        return jList;
//
//    }
//}
