package rastak.train.ws.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rastak.train.config.JwtService;
import rastak.train.shared.MyApiResponse;
import rastak.train.shared.UserException;
import rastak.train.token.Token;
import rastak.train.token.TokenRepository;
import rastak.train.token.TokenType;
import rastak.train.ws.model.dto.UserDto;
import rastak.train.ws.model.entity.UserEntity;
import rastak.train.ws.model.request.SignUp;
import rastak.train.ws.model.response.UserDeleteResponse;
import rastak.train.ws.model.response.UserResponse;
import rastak.train.ws.repository.UserRepository;
import rastak.train.ws.service.UserService;
import rastak.train.ws.util.UserUtils;

import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    //    private final To
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserUtils userUtils;

    public UserServiceImpl(TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, UserRepository userRepository, UserUtils userUtils) {
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
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
            logger.info("token: " + jwtToken);
            logger.info("refreshToken: " + refreshToken);
            savedUserToken(storedUserEntity, jwtToken);
        } catch (Exception exception) {
            logger.info(exception.getMessage());
            throw new UserException("DataBase IO error", HttpStatus.BAD_REQUEST);
        }


        return userUtils.createResponse(userUtils.convert(storedUserEntity), HttpStatus.CREATED);
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
            throw new UserException("username 0r password not correct", HttpStatus.BAD_REQUEST);
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
}
