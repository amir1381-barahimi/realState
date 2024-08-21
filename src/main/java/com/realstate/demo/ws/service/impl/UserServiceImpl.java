package com.realstate.demo.ws.service.impl;

import com.realstate.demo.config.JwtService;
import com.realstate.demo.shared.MyApiResponse;
import com.realstate.demo.shared.UserException;
import com.realstate.demo.token.Token;
import com.realstate.demo.token.TokenRepository;
import com.realstate.demo.token.TokenType;
import com.realstate.demo.ws.model.dto.UserDto;
import com.realstate.demo.ws.model.entity.UserEntity;
import com.realstate.demo.ws.model.request.JSONRoleRequest;
import com.realstate.demo.ws.model.request.SignUp;
import com.realstate.demo.ws.model.response.UserDeleteResponse;
import com.realstate.demo.ws.model.response.UserResponse;
import com.realstate.demo.ws.repository.UserRepository;
import com.realstate.demo.ws.service.UserService;
import com.realstate.demo.ws.util.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserUtils userUtils;

    public UserServiceImpl(TokenRepository tokenRepository, JwtService jwtService, UserRepository userRepository, UserUtils userUtils) {
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.userUtils = userUtils;
    }

    @Override
    public ResponseEntity<MyApiResponse> getUserByPublicId(String publicId) {
        UserEntity userEntity = userRepository.findByPublicId(publicId);
        if (userEntity == null) {
            throw new UserException("User not found", HttpStatus.NOT_FOUND);
        }
        UserResponse userResponse = userUtils.convert(userEntity);
        return userUtils.createResponse(userResponse, HttpStatus.OK);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<UserEntity> userEntities = userRepository.findAll();
        if (userEntities == null) {
            throw new UserException("Any user not found", HttpStatus.NOT_FOUND);
        }
        return userEntities.stream().map(userEntity -> new ModelMapper().map(userEntity, UserDto.class)).toList();
    }

    @Override
    public ResponseEntity<MyApiResponse> addUser(SignUp signUp) {
        UserDto userDto = userUtils.convert(signUp);
        if (userDto == null) {
            throw new UserException("Invalid Input", HttpStatus.BAD_REQUEST);
        }
        UserEntity existedUserEntity = userRepository.findByUsername(signUp.getUsername());
        if (existedUserEntity != null) {
            throw new UserException("User with this username was exist", HttpStatus.CONFLICT);
        }
        UserEntity userEntity = userUtils.convert(userDto);
        UserEntity storedUserEntity;
        try {
            storedUserEntity = userRepository.save(userEntity);
            var jwtToken = jwtService.generateToken(userEntity);
            var refreshToken = jwtService.generateRefreshToken(userEntity);
            savedUserToken(storedUserEntity, jwtToken);
        } catch (Exception exception) {
            logger.info(exception.getMessage());
            throw new UserException("DataBase IO error", HttpStatus.BAD_REQUEST);
        }
        return userUtils.createResponse(userUtils.convert(storedUserEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MyApiResponse> deleteUser(String publicId) {
        int deleteUser = userRepository.deleteByPublicId(publicId);
        if (deleteUser == 0) {
            throw new UserException("User not found with this id", HttpStatus.NOT_FOUND);
        }
        UserDeleteResponse userDeleteResponse = userUtils.createDeleteResponse(publicId);
        return userUtils.createResponse(userDeleteResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MyApiResponse> loginUser(String username, String password) {
        UserEntity existedUserEntity = userRepository.findByUsername(username);
        if (existedUserEntity == null) {
            throw new UserException("User with this username not found, please signup", HttpStatus.NOT_FOUND);
        }
        if (!password.equals(existedUserEntity.getPassword())) {
            throw new UserException("username or password not correct", HttpStatus.BAD_REQUEST);
        }
        var jwtToken = jwtService.generateToken(existedUserEntity);
        var refreshToken = jwtService.generateRefreshToken(existedUserEntity);
        revokeAllUserTokens(existedUserEntity);
        savedUserToken(existedUserEntity, jwtToken);
        logger.info("token: " + jwtToken);
        logger.info("refreshToken: " + refreshToken);
        logger.info("User with username: " + username + " login");
        UserResponse userResponse = userUtils.convert(existedUserEntity);
        return userUtils.createResponse(userResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MyApiResponse> updateUser(SignUp signUp, String publicId) {
        UserEntity existedUserEntity = userRepository.findByPublicId(publicId);
        if (existedUserEntity == null) {
            throw new UserException("User not found", HttpStatus.NOT_FOUND);
        }
        UserEntity updateUserEntity;
        updateUserEntity = userUtils.update(existedUserEntity, signUp);
        if (updateUserEntity == null) {
            throw new UserException("Invalid Input", HttpStatus.NOT_FOUND);
        }
        try {
            updateUserEntity = userRepository.save(updateUserEntity);
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            throw new UserException("Database IO Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return userUtils.createResponse(userUtils.convert(updateUserEntity), HttpStatus.OK);
    }


    private void savedUserToken(UserEntity storedUserEntity, String jwtToken) {
        var token = Token.builder()
                .user(storedUserEntity)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserEntity existedUserEntity) {
        var validUserToken = tokenRepository.findAllValidTokenByUser(existedUserEntity.getId());
        if (validUserToken.isEmpty())
            return;
        validUserToken.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserToken);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var existedUserEntity = this.userRepository.findByUsername(username);
            if (jwtService.isTokenValid(refreshToken, existedUserEntity)) {
                var accessToken = jwtService.generateToken(existedUserEntity);
                revokeAllUserTokens(existedUserEntity);
                savedUserToken(existedUserEntity, accessToken);
            }
        }
    }

    @Override
    public void setRole(String id, JSONRoleRequest r) {
        UserEntity user = userRepository.findByPublicId(id);
        user.setRole(r.getRole());
        userRepository.save(user);
    }
}
