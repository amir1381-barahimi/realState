package com.realstate.demo.shared;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UserException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;

    public UserException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
