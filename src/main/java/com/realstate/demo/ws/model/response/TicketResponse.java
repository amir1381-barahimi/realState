package com.realstate.demo.ws.model.response;

import com.realstate.demo.ws.model.enums.Status;
import lombok.Data;

@Data
public class TicketResponse {
    private String publicId;
    private String title;
    private String Description;
    private Status status;
}
