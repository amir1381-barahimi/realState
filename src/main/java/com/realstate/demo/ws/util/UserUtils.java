package com.realstate.demo.ws.util;

import com.realstate.demo.config.JwtService;
import com.realstate.demo.shared.MyApiResponse;
import com.realstate.demo.shared.UserException;
import com.realstate.demo.shared.Utils;
import com.realstate.demo.ws.model.dto.UserDto;
import com.realstate.demo.ws.model.entity.UserEntity;
import com.realstate.demo.ws.model.enums.Role;
import com.realstate.demo.ws.model.enums.Status;
import com.realstate.demo.ws.model.request.JSONSignUp;
import com.realstate.demo.ws.model.request.SignUp;
import com.realstate.demo.ws.model.response.JSONUserDeleteResponse;
import com.realstate.demo.ws.model.response.JSONUserResponse;
import com.realstate.demo.ws.model.response.UserDeleteResponse;
import com.realstate.demo.ws.model.response.UserResponse;
import com.realstate.demo.ws.repository.TicketRepository;
import com.realstate.demo.ws.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserUtils {

    private final ModelMapper modelMapper = new ModelMapper();
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    private final Utils utils;

    public UserUtils(JwtService jwtService, UserRepository userRepository, TicketRepository ticketRepository, Utils utils) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.utils = utils;
    }

    public JSONUserResponse convert(UserEntity userEntity, String jwtToken, String refreshToken) {
        if (userEntity == null) {
            return null;
        }
        JSONUserResponse JSONUserResponse = modelMapper.map(userEntity, JSONUserResponse.class);
        JSONUserResponse.setToken(jwtToken);
        JSONUserResponse.setRefreshToken(refreshToken);

        return JSONUserResponse;
    }
    public JSONUserResponse convert(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        JSONUserResponse JSONUserResponse = modelMapper.map(userEntity, JSONUserResponse.class);

        return JSONUserResponse;
    }

    public ResponseEntity<MyApiResponse> createResponse(Object userResponse, HttpStatus httpStatus) {
        if (userResponse == null || httpStatus == null) {
            return null;
        }
        MyApiResponse apiResponse = new MyApiResponse();
        apiResponse.setAction(true);
        apiResponse.setDate(new Date());
        apiResponse.setMessage("");
        apiResponse.setResult(userResponse);
        return new ResponseEntity<>(apiResponse, httpStatus);
    }


    public UserDto convert(JSONSignUp signUp) {
        if (isValidRequestModel(signUp)) {
            return createDtoModel(signUp);
        }
        return null;
    }

    private UserDto createDtoModel(JSONSignUp signUp) {
        UserDto userDto = modelMapper.map(signUp, UserDto.class);
        userDto.setPublicId(utils.getPublicId());
        return userDto;
    }

    public UserEntity convert(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setRole(Role.CUSTOMER);
        return userEntity;
    }

    public JSONUserDeleteResponse createDeleteResponse(String publicId) {
        JSONUserDeleteResponse JSONUserDeleteResponse = new JSONUserDeleteResponse();
        JSONUserDeleteResponse.setPublicId(publicId);
        JSONUserDeleteResponse.setStatus("user with publicId {" + publicId + "} deleted");
        return JSONUserDeleteResponse;
    }

    public UserEntity update(UserEntity existedUserEntity, JSONSignUp signUp) {
//        if (signUp.getRole() != null)
//            existedUserEntity.setRole(signUp.getRole());
        if (signUp.getUsername() != null)
            existedUserEntity.setUsername(signUp.getUsername());
        if (signUp.getFullname() != null)
            existedUserEntity.setFullname(signUp.getFullname());
        if (signUp.getPassword() != null)
            existedUserEntity.setPassword(signUp.getPassword());
        return existedUserEntity;
    }

    private boolean isValidRequestModel(JSONSignUp signUp) {
        boolean flag = true;
        if (signUp == null) {
            return false;
        }
        if (signUp.getFullname().isEmpty()) {
            return false;
        }
        if (signUp.getUsername().isEmpty()) {
            return false;
        }
        if (signUp.getPassword().isEmpty()) {
            return false;
        }
//        if (signUp.getRole() == null) {
//            return false;
//        }
        return flag;
    }

    public UserEntity getCurrentUser(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String username;
        if (token != null && token.startsWith("Bearer ")) {
            String tokenWithoutBearer = token.substring(7);
            username = jwtService.extractUsername(tokenWithoutBearer);
        } else {
            throw new UserException("Invalid or missing token", HttpStatus.UNAUTHORIZED);
        }
        UserEntity currentUser = userRepository.findByUsername(username);
        if (currentUser == null) {
            throw new UserException("User not found", HttpStatus.NOT_FOUND);
        }
        return currentUser;
    }

    public UserEntity findSupport(){
        List<UserEntity> supportUsers = userRepository.findByRole(Role.SUPPORT);
        Map<UserEntity, Integer> openTicketCount = new HashMap<>();
        for (UserEntity user : supportUsers){
            openTicketCount.put(user, ticketRepository.countBySupportAndStatus(user, Status.OPEN));
        }

        UserEntity leastLoadedSupport = null;
        int minOpenTickets = Integer.MAX_VALUE;
        for (Map.Entry<UserEntity, Integer> entry : openTicketCount.entrySet()) {
            if (entry.getValue() < minOpenTickets) {
                minOpenTickets = entry.getValue();
                leastLoadedSupport = entry.getKey();
            }
        }
        return leastLoadedSupport;
    }
}
