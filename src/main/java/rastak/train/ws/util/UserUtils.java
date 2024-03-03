package rastak.train.ws.util;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import rastak.train.shared.MyApiResponse;
import rastak.train.shared.Utils;
import rastak.train.ws.model.dto.UserDto;
import rastak.train.ws.model.entity.UserEntity;
import rastak.train.ws.model.request.SignUp;
import rastak.train.ws.model.response.UserDeleteResponse;
import rastak.train.ws.model.response.UserResponse;

import java.util.Date;

@Component
public class UserUtils {

    private final ModelMapper modelMapper = new ModelMapper();

    private final Utils utils;

    public UserUtils(Utils utils) {
        this.utils = utils;
    }

    public UserResponse convert(UserEntity userEntity){
        if (userEntity == null){
            return null;
        }
        UserResponse userResponse = modelMapper.map(userEntity, UserResponse.class);
        return userResponse;
    }

    public ResponseEntity<MyApiResponse> createResponse(Object userResponse, HttpStatus httpStatus){
        if (userResponse == null || httpStatus == null){
            return null;
        }
        MyApiResponse apiResponse = new MyApiResponse();
        apiResponse.setAction(true);
        apiResponse.setDate(new Date());
        apiResponse.setMessage("");
        apiResponse.setResult(userResponse);
        return new ResponseEntity<>(apiResponse, httpStatus);
    }


    public UserDto convert(SignUp signUp){
        if (isValidRequestModel(signUp)){
            return createDtoModel(signUp);
        }
        return null;
    }

    private UserDto createDtoModel(SignUp signUp){
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
        return flag;
    }
}
