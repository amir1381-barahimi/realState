package com.realstate.demo.ws.model.request;

import lombok.Data;

@Data
public class JSONChangePassword {

    private String oldPassword;
    private String newPassword;
}
