package rastak.train.ws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rastak.train.shared.MyApiResponse;
import rastak.train.ws.model.request.Login;
import rastak.train.ws.model.request.SignUp;
import rastak.train.ws.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthenticateController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public AuthenticateController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MyApiResponse> signUp(@RequestBody SignUp signUp) {
        logger.info("add new user to database  with username: {}", signUp.getUsername());
        return userService.addUser(signUp);
    }

    @PostMapping("/login")
    public ResponseEntity<MyApiResponse> login(@RequestBody Login login) {
        logger.info("User with username: " + login.getUsername() + " try to login");
        return userService.loginUser(login.getUsername(), login.getPassword());
    }

}
