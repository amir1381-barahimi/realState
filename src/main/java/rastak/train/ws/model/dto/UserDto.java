package rastak.train.ws.model.dto;

import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String publicId;
    private String fullname;
    private String username;
    private String lastname;
}
