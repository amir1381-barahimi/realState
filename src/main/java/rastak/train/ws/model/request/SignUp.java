package rastak.train.ws.model.request;

import lombok.Data;
import rastak.train.ws.model.enums.UserRole;

import java.security.SecureRandom;

@Data
public class SignUp {
    private String username;
    private String password;
    private String fullname;
    private UserRole userRole;
}
