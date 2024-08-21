package com.realstate.demo.ws.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Login {
    @Schema(description = "Username that has already registered", example = "Alirez")
    private String username;
    @Schema(description = "password for entred username", example = "12@alireza#21")
    private String password;
}
