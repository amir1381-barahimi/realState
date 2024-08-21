package com.realstate.demo.ws.controller;

import com.realstate.demo.ws.model.request.JSONRoleRequest;
import com.realstate.demo.ws.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class SetRoleController {

    private final UserService service;

    public SetRoleController(UserService service) {
        this.service = service;
    }

    @PostMapping("/{id}")
    public ResponseEntity setRole(@PathVariable("id")String id, @RequestBody JSONRoleRequest r){
        service.setRole(id, r);
        return new ResponseEntity(HttpStatus.OK);
    }
}
