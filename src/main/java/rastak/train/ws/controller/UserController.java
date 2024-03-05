package rastak.train.ws.controller;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rastak.train.shared.MyApiResponse;
import rastak.train.ws.model.dto.UserDto;
import rastak.train.ws.model.request.Login;
import rastak.train.ws.model.request.SignUp;
import rastak.train.ws.model.response.UserResponse;
import rastak.train.ws.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{publicId}")
    public ResponseEntity<MyApiResponse> getUserById(@PathVariable String publicId) {
        return userService.getUserByPublicId(publicId);
    }

    @GetMapping
    public List<UserResponse> getAllUser() {
        List<UserDto> userDtos = userService.getAllUser();
        return userDtos.stream().map(userDto -> new ModelMapper().map(userDto, UserResponse.class)).toList();
    }

    @PostMapping("/signup")
    public ResponseEntity<MyApiResponse> signUp(@Valid @RequestBody SignUp signUp) {
        logger.info("add new user to database  with username: {}", signUp.getUsername());
        return userService.addUser(signUp);
    }

    @PostMapping("/login")
    public ResponseEntity<MyApiResponse> login(@RequestBody Login login) {
        logger.info("User with username: " + login.getUsername() + " try to login");
        return userService.loginUser(login.getUsername(), login.getPassword());
    }

    @DeleteMapping("/{publicId}")
    @Transactional
    public ResponseEntity<MyApiResponse> deleteUser(@PathVariable String publicId) {
        logger.info("delete user by public: {}", publicId);
        return userService.deleteUser(publicId);
    }
}
