package com.realstate.demo.ws.model.response;

import lombok.Data;

@Data
public class JSONUserResponse {

    private String publicId;
    private String fullname;
    private String username;
    private String password;
    private String token;
    private String refreshToken;
}
