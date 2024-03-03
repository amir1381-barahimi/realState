package rastak.train.ws.service.impl;

import lombok.Setter;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rastak.train.shared.MyApiResponse;
import rastak.train.shared.UserException;
import rastak.train.ws.model.dto.UserDto;
import rastak.train.ws.model.entity.UserEntity;
import rastak.train.ws.model.request.SignUp;
import rastak.train.ws.model.response.UserDeleteResponse;
import rastak.train.ws.model.response.UserResponse;
import rastak.train.ws.repository.UserRepository;
import rastak.train.ws.service.UserService;
import rastak.train.ws.util.UserUtils;

import java.util.List;

@Service
public class USerServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(USerServiceImpl.class);
    private final UserRepository userRepository;
    private final UserUtils userUtils;

    public USerServiceImpl(UserRepository userRepository, UserUtils userUtils) {
        this.userRepository = userRepository;
        this.userUtils = userUtils;
    }

    @Override
    public ResponseEntity<MyApiResponse> getUSerByPublicId(String publicId) {
        UserEntity userEntity = userRepository.findByPublicId(publicId);
        if (userEntity == null){
            throw new UserException("User not found", HttpStatus.NOT_FOUND);
        }
        UserResponse userResponse = userUtils.convert(userEntity);
        return userUtils.createResponse(userResponse, HttpStatus.OK);
    }
    @Override
    public List<UserDto> getAllUser() {
        List<UserEntity> userEntities = userRepository.findAll();
        if (userEntities == null){
            throw new UserException("Any user not found", HttpStatus.NOT_FOUND);
        }
        return userEntities.stream().map(userEntity -> new ModelMapper().map(userEntity, UserDto.class)).toList();
    }

    @Override
    public ResponseEntity<MyApiResponse> addUser(SignUp signUp) {
        UserDto userDto = userUtils.convert(signUp);
        if (userDto == null){
            throw new UserException("Invalid Input", HttpStatus.BAD_REQUEST);
        }
        UserEntity existedUserEntity = userRepository.findByUsername(signUp.getUsername());
        if (existedUserEntity != null){
            throw new UserException("User with this username was exist", HttpStatus.CONFLICT);
        }
        UserEntity userEntity = userUtils.convert(userDto);
        UserEntity storedUserEntity;
        try {
            storedUserEntity= userRepository.save(userEntity);
        }catch (Exception exception){
            logger.info(exception.getMessage());
            throw new UserException("DataBase IO error" , HttpStatus.BAD_REQUEST);
        }
        return userUtils.createResponse(userUtils.convert(storedUserEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MyApiResponse> deleteUser(String publicId) {
        int deleteUser = userRepository.deleteByPublicId(publicId);
        if (deleteUser == 0 ){
            throw new UserException("User not found with this id", HttpStatus.NOT_FOUND);
        }
        UserDeleteResponse userDeleteResponse = userUtils.createDeleteResponse(publicId);
        return userUtils.createResponse(userDeleteResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MyApiResponse> loginUser(String username, String password) {
        UserEntity existedUserEntity = userRepository.findByUsername(username);
        if (existedUserEntity == null){
            throw new UserException("User with this username not found, please signup", HttpStatus.NOT_FOUND);
        }
        if (!password.equals(existedUserEntity.getPassword())){
            throw new UserException("username 0r password not correct", HttpStatus.BAD_REQUEST);
        }
        logger.info("User with username: "+username+"login");
        UserResponse userResponse = userUtils.convert(existedUserEntity);
        return userUtils.createResponse(userResponse, HttpStatus.OK);
    }
}
