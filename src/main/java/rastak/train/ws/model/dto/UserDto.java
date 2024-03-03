package rastak.train.ws.model.dto;

import lombok.Data;
import rastak.train.ws.model.enums.UserRole;

@Data
public class UserDto {
    private long id;
    private String publicId;
    private String fullname;
    private String username;
    private String password;
    private UserRole userRole;
}
