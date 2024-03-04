package rastak.train.ws.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import rastak.train.ws.model.enums.UserRole;

@Data
public class UserResponse {

    private String publicId;
    private String fullname;
    private String username;
    private String password;
    private UserRole userRole;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("access_token")
    private String refreshToken;
}
