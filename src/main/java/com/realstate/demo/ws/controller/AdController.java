package com.realstate.demo.ws.controller;

import com.realstate.demo.ws.model.request.JSONSAdvertisementRequest;
import com.realstate.demo.ws.model.response.JSONAdvertisementResponse;
import com.realstate.demo.ws.service.AdService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/ad")
public class AdController {

    private final AdService service;

    public AdController(AdService service) {
        this.service = service;
    }

    @PostMapping
    public JSONAdvertisementResponse createAd(@RequestBody JSONSAdvertisementRequest r, HttpServletRequest request){
        return service.createAd(r.getTitle(),r.getDescription(),r.getHomeId(),r.getContactNumber(),r.getListingStatus(),r.getPropertyStatus(),  request);
    }

    @GetMapping("/{id}")
    public JSONAdvertisementResponse getAd(@PathVariable("id")long id){
        return service.getAd(id);
    }

    @GetMapping("/all")
    public List<JSONAdvertisementResponse> getAllAd(){
        return service.getAllAdd();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAd(@PathVariable("id") long id){
        service.deleteAd(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
