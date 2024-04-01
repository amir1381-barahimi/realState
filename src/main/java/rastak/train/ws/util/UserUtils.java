package rastak.train.ws.util;

import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import rastak.train.config.JwtService;
import rastak.train.shared.MyApiResponse;
import rastak.train.shared.TicketException;
import rastak.train.shared.UserException;
import rastak.train.shared.Utils;
import rastak.train.ws.model.dto.UserDto;
import rastak.train.ws.model.entity.UserEntity;
import rastak.train.ws.model.enums.Role;
import rastak.train.ws.model.enums.Status;
import rastak.train.ws.model.request.SignUp;
import rastak.train.ws.model.response.UserDeleteResponse;
import rastak.train.ws.model.response.UserResponse;
import rastak.train.ws.repository.TicketRepository;
import rastak.train.ws.repository.UserRepository;

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

    public UserResponse convert(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        UserResponse userResponse = modelMapper.map(userEntity, UserResponse.class);
        return userResponse;
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


    public UserDto convert(SignUp signUp) {
        if (isValidRequestModel(signUp)) {
            return createDtoModel(signUp);
        }
        return null;
    }

    private UserDto createDtoModel(SignUp signUp) {
        UserDto userDto = modelMapper.map(signUp, UserDto.class);
        userDto.setPublicId(utils.getPublicId());
        return userDto;
    }

    public UserEntity convert(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        return userEntity;
    }

    public UserDeleteResponse createDeleteResponse(String publicId) {
        UserDeleteResponse userDeleteResponse = new UserDeleteResponse();
        userDeleteResponse.setPublicId(publicId);
        userDeleteResponse.setStatus("user with publicId {" + publicId + "} deleted");
        return userDeleteResponse;
    }

    public UserEntity update(UserEntity existedUserEntity, SignUp signUp) {
        if (signUp.getRole() != null)
            existedUserEntity.setRole(signUp.getRole());
        if (signUp.getUsername() != null)
            existedUserEntity.setUsername(signUp.getUsername());
        if (signUp.getFullname() != null)
            existedUserEntity.setFullname(signUp.getFullname());
        if (signUp.getPassword() != null)
            existedUserEntity.setPassword(signUp.getPassword());
        return existedUserEntity;
    }

    private boolean isValidRequestModel(SignUp signUp) {
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
        if (signUp.getRole() == null) {
            return false;
        }
        return flag;
    }

    public UserEntity getCurrentUser(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String username;
        if (token != null && token.startsWith("Bearer ")) {
            String tokenWithoutBearer = token.substring(7);
            username = jwtService.extractUsername(tokenWithoutBearer);
        } else {
            throw new TicketException("Invalid or missing token", HttpStatus.UNAUTHORIZED);
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
