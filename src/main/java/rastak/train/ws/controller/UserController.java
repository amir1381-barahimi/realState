package rastak.train.ws.controller;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rastak.train.shared.MyApiResponse;
import rastak.train.ws.model.dto.UserDto;
import rastak.train.ws.model.response.UserResponse;
import rastak.train.ws.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {


    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{publicId}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<MyApiResponse> getUserById(@PathVariable String publicId) {
        return userService.getUserByPublicId(publicId);
    }
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public List<UserResponse> getAllUser() {
        List<UserDto> userDtos = userService.getAllUser();
        return userDtos.stream().map(userDto -> new ModelMapper().map(userDto, UserResponse.class)).toList();
    }
    @DeleteMapping("/{publicId}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Transactional
    public ResponseEntity<MyApiResponse> deleteUser(@PathVariable String publicId) {
        logger.info("delete user by public: {}", publicId);
        return userService.deleteUser(publicId);
    }
}
