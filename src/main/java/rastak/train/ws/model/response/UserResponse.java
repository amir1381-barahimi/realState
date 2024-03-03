package rastak.train.ws.model.response;

import lombok.Data;

@Data
public class UserResponse {

    private String publicId;
    private String fullname;
    private String username;
    private String password;
}
