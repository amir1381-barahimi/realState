//package com.realstate.demo.ws.controller;
//
//import com.realstate.demo.ws.model.request.JSONHomeRequest;
//import com.realstate.demo.ws.model.response.JSONHomeResponse;
//import com.realstate.demo.ws.service.HomeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/home")
//public class HomeController {
//
//    @Autowired
//    private HomeService homeService;
//
//    @PostMapping("/create")
//    public JSONHomeResponse createHome(@RequestBody JSONHomeRequest r) {
//        return homeService.createHome(r.getAddress(), r.getCity(), r.getAmenities(), r.getPrice(), r.getSize(), r.getType(), r.getNumberOfRoom(), r.getYearBuilt());
//    }
//
//    @GetMapping("/all")
//    public List<JSONHomeResponse> getAllHome() {
//        return homeService.getAllHome();
//    }
//
//    @GetMapping("/{id}")
//    public JSONHomeResponse getHome(@PathVariable("id") Long id){
//        return homeService.getHome(id);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity deleteHome(@PathVariable("id") long id){
//        return homeService.deleteHome(id);
//    }
//}
