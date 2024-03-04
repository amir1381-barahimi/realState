package rastak.train.ws.service;

import org.springframework.http.ResponseEntity;
import rastak.train.shared.MyApiResponse;
import rastak.train.ws.model.dto.UserDto;
import rastak.train.ws.model.request.SignUp;

import java.util.List;

public interface UserService {

    ResponseEntity<MyApiResponse> getUserByPublicId(String publicId);

    List<UserDto> getAllUser();

    ResponseEntity<MyApiResponse> addUser(SignUp signUp);

    ResponseEntity<MyApiResponse> deleteUser(String publicId);

    ResponseEntity<MyApiResponse> loginUser(String username, String password);
}
