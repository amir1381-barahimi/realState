package rastak.train.ws.model.response;

import lombok.Data;
import rastak.train.ws.model.enums.Role;

@Data
public class UserResponse {

    private String publicId;
    private String fullname;
    private String username;
    private String password;
    private Role role;
//    @JsonProperty("access_token")
//    private String accessToken;
//    @JsonProperty("access_token")
//    private String refreshToken;
}
