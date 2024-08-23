package com.realstate.demo.ws.controller;

import com.realstate.demo.shared.MyApiResponse;
import com.realstate.demo.ws.model.dto.UserDto;
import com.realstate.demo.ws.model.request.JSONSignUp;
import com.realstate.demo.ws.model.response.JSONUserResponse;
import com.realstate.demo.ws.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER') or hasRole('SUPPORT')")
public class UserController {


    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "get a user", description = "Get User By PublicId or Username,ADMIN can access to this method", tags = {"USER"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The request succeeded.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "The server cannot find the requested resource. In the browser, this means the URL is not recognized.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "The server has encountered a situation it does not know how to handle.", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/{publicId}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<MyApiResponse> getUserById(@PathVariable String publicId) {
        return userService.getUserByPublicId(publicId);
    }
    @Operation(summary = "get all user", description = "Get All User, ADMIN can access to this method ", tags = {"USER"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The request succeeded.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "The server cannot find the requested resource. In the browser, this means the URL is not recognized.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "The server has encountered a situation it does not know how to handle.", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public List<JSONUserResponse> getAllUser() {
        List<UserDto> userDtos = userService.getAllUser();
        return userDtos.stream().map(userDto -> new ModelMapper().map(userDto, JSONUserResponse.class)).toList();
    }
    @Operation(summary = "update user", description = "update User By Id from Database, Only ADMIN can access to this method", tags = {"USER"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The request succeeded.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request due to something that is perceived to be a client error (e.g., malformed request syntax, invalid request message framing, or deceptive request routing", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "The server has encountered a situation it does not know how to handle.", content = {@Content(mediaType = "application/json")})
    })
    @PutMapping("/{publicId}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<MyApiResponse> updateUser(@RequestBody JSONSignUp signUp, @PathVariable String publicId){
        return userService.updateUser(signUp, publicId);
    }
    @Operation(summary = "delete user", description = "delete User By Id from Database, Only ADMIN can access to this method", tags = {"USER"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The request succeeded.", content = {@Content(mediaType = "application/json", schema = @Schema(type = "object"))}),
            @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request due to something that is perceived to be a client error (e.g., malformed request syntax, invalid request message framing, or deceptive request routing).", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = """
                    The server has encountered a situation it does not know how to handle.
                    """, content = {@Content(mediaType = "application/json")})
    })
    @DeleteMapping("/{publicId}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Transactional
    public ResponseEntity<MyApiResponse> deleteUser(@PathVariable String publicId) {
        logger.info("delete user by public: {}", publicId);
        return userService.deleteUser(publicId);
    }
}
