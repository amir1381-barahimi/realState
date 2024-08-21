package com.realstate.demo.ws.model.dto;

import com.realstate.demo.ws.model.enums.Role;
import lombok.Data;


@Data
public class UserDto {
    private long id;
    private String publicId;
    private String fullname;
    private String username;
    private String password;
    private Role role;
}
