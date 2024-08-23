//package com.realstate.demo.ws.service;
//
//
//import com.realstate.demo.ws.model.enums.Amenities;
//import com.realstate.demo.ws.model.enums.TypeOfHome;
//import com.realstate.demo.ws.model.response.JSONHomeResponse;
//import org.springframework.http.ResponseEntity;
//
//
//import java.util.List;
//
//public interface HomeService {
//    JSONHomeResponse createHome(String address, String city, Amenities amenities, String price, int size, TypeOfHome type, String numberOfRoom, String yearBuilt);
//
//    List<JSONHomeResponse> getAllHome();
//
//    JSONHomeResponse getHome(long id);
//
//    ResponseEntity deleteHome(long id);
//}
