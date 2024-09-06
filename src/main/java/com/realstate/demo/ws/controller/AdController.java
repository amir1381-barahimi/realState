package com.realstate.demo.ws.controller;

import com.realstate.demo.ws.model.request.JSONSAdvertisementRequest;
import com.realstate.demo.ws.model.response.JSONAdvertisementResponse;
import com.realstate.demo.ws.service.AdService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
        return service.createAd(r.getTitle(),r.getDescription(),r.getStreet(),r.getCity(),r.getPostalCode(),r.getCountry(),r.getStatePrice(),r.getStateType(),r.getNumberBath(),r.getNumberBed(),r.getEmail(),r.getPhone(),r.getImage(),r.getNumberParking(),request);
    }

    @GetMapping("/{id}")
    public JSONAdvertisementResponse getAd(@PathVariable("id")long id){
        return service.getAd(id);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<InputStreamResource> getAdImage(@PathVariable("id")long id) throws IOException {
        JSONAdvertisementResponse jsonAdvertisementResponse =  service.getAd(id);
        String imageUrl = jsonAdvertisementResponse.getImage().replace("\\","/");
        imageUrl = "file:///"+ imageUrl;
        URL url = new URL(imageUrl);
        InputStream imageStream = url.openStream();

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(imageStream));
    }


    @GetMapping("/all")
    public List<JSONAdvertisementResponse> getAllAd(@RequestParam(value = "city", required = false) String city){
        return service.getAllAdd(city);
    }
    @GetMapping("/all/user")
    public List<JSONAdvertisementResponse> getAllAdByUser(HttpServletRequest request){
        return service.getAllAdByUser(request);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAd(@PathVariable("id") long id){
        service.deleteAd(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
