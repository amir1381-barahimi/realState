package rastak.train.ws.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "post user", description = "Add New User to Database", tags = {"AUTH"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The request succeeded, and a new resource was created as a result.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "The server cannot find the requested resource. In the browser, this means the URL is not recognized.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request due to something that is perceived to be a client error (e.g., malformed request syntax, invalid request message framing, or deceptive request routing", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "The server has encountered a situation it does not know how to handle.", content = {@Content(mediaType = "application/json")}),
    })
    @PostMapping("/signup")
    public ResponseEntity<MyApiResponse> signUp(@RequestBody SignUp signUp) {
        logger.info("add new user to database  with username: {}", signUp.getUsername());
        return userService.addUser(signUp);
    }

    @Operation(summary = "login user", description = "login user with username and password", tags = {"AUTH"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The request succeeded, and a new resource was created as a result.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request due to something that is perceived to be a client error (e.g., malformed request syntax, invalid request message framing, or deceptive request routing", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "The server has encountered a situation it does not know how to handle.", content = {@Content(mediaType = "application/json")}),
    })
    @PostMapping("/login")
    public ResponseEntity<MyApiResponse> login(@RequestBody Login login) {
        logger.info("User with username: " + login.getUsername() + " try to login");
        return userService.loginUser(login.getUsername(), login.getPassword());
    }

}
