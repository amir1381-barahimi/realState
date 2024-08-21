package com.realstate.demo.ws.model.request;

import com.realstate.demo.ws.model.enums.Status;
import lombok.Data;
@Data
public class TicketRequest {

    private String title;
    private String Description;
    private Status status;
}
