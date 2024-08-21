package com.realstate.demo.ws.model.response;

import com.realstate.demo.ws.model.enums.Role;
import lombok.Data;

@Data
public class UserResponse {

    private String publicId;
    private String fullname;
    private String username;
    private String password;
    private Role role;
}
