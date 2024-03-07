package rastak.train.ws.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rastak.train.shared.MyApiResponse;
import rastak.train.ws.model.dto.UserDto;
import rastak.train.ws.model.request.SignUp;
import rastak.train.ws.model.response.UserResponse;
import rastak.train.ws.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
public class UserController {


    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "get a user", description = "Get User By PublicId or Username", tags = {"USER"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The request succeeded.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "The server cannot find the requested resource. In the browser, this means the URL is not recognized.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "The server has encountered a situation it does not know how to handle.", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/{publicId}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('user:read')")
    public ResponseEntity<MyApiResponse> getUserById(@PathVariable String publicId) {
        return userService.getUserByPublicId(publicId);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('user:read')")
    public List<UserResponse> getAllUser() {
        List<UserDto> userDtos = userService.getAllUser();
        return userDtos.stream().map(userDto -> new ModelMapper().map(userDto, UserResponse.class)).toList();
    }
    @PutMapping("/{publicId}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<MyApiResponse> updateUser(@RequestBody SignUp signUp,@PathVariable String publicId){
        return userService.updateUser(signUp, publicId);
    }
    @DeleteMapping("/{publicId}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Transactional
    public ResponseEntity<MyApiResponse> deleteUser(@PathVariable String publicId) {
        logger.info("delete user by public: {}", publicId);
        return userService.deleteUser(publicId);
    }
}
