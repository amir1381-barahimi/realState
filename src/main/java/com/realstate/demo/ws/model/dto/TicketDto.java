package com.realstate.demo.ws.model.dto;

import com.realstate.demo.ws.model.entity.UserEntity;
import com.realstate.demo.ws.model.enums.Status;
import lombok.Data;

@Data
public class TicketDto {

    private long id;
    private String publicId;
    private String title;
    private String Description;
    private Status status;
    private UserEntity user;
    private UserEntity support;
}
