package com.realstate.demo.ws.service;

import com.realstate.demo.shared.MyApiResponse;
import com.realstate.demo.ws.model.dto.UserDto;
import com.realstate.demo.ws.model.request.JSONRoleRequest;
import com.realstate.demo.ws.model.request.JSONSignUp;
import com.realstate.demo.ws.model.request.JSONChangePassword;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<MyApiResponse> getUserByPublicId(String publicId);

    List<UserDto> getAllUser();

    ResponseEntity<MyApiResponse> addUser(JSONSignUp signUp);

    ResponseEntity<MyApiResponse> deleteUser(String publicId);

    ResponseEntity<MyApiResponse> loginUser(String username, String password);

    ResponseEntity<MyApiResponse> updateUser(JSONSignUp signUp, String publicId);

    void setRole(String id, JSONRoleRequest r);

    ResponseEntity<MyApiResponse> changePassword(JSONChangePassword jsonChangePassword, HttpServletRequest request);

}
