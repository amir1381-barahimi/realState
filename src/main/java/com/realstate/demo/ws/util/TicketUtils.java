package com.realstate.demo.ws.util;

import com.realstate.demo.shared.MyApiResponse;
import com.realstate.demo.shared.Utils;
import com.realstate.demo.ws.model.dto.TicketDto;
import com.realstate.demo.ws.model.entity.TicketEntity;
import com.realstate.demo.ws.model.request.TicketRequest;
import com.realstate.demo.ws.model.response.TicketDeleteResponse;
import com.realstate.demo.ws.model.response.TicketResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import java.util.Date;

@Component
public class TicketUtils {
    private final ModelMapper modelMapper = new ModelMapper();

    private final Utils utils;

    public TicketUtils(Utils utils) {
        this.utils = utils;
    }

    public TicketResponse convert(TicketEntity ticketEntity) {
        if (ticketEntity == null) {
            return null;
        }
        TicketResponse ticketResponse = modelMapper.map(ticketEntity, TicketResponse.class);
        return ticketResponse;
    }

    public ResponseEntity<MyApiResponse> createResponse(Object ticketResponse, HttpStatus httpStatus) {
        if (ticketResponse == null || httpStatus == null) {
            return null;
        }
        MyApiResponse apiResponse = new MyApiResponse();
        apiResponse.setAction(true);
        apiResponse.setDate(new Date());
        apiResponse.setMessage("");
        apiResponse.setResult(ticketResponse);
        return new ResponseEntity<>(apiResponse, httpStatus);
    }

    public TicketDto convert(TicketRequest ticketRequest) {
        if (isValidRequestModel(ticketRequest)) {
            return createDtoModel(ticketRequest);
        }
        return null;
    }

    private TicketDto createDtoModel(TicketRequest ticketRequest) {
        TicketDto ticketDto = modelMapper.map(ticketRequest, TicketDto.class);
        ticketDto.setPublicId(utils.getPublicId());
        return ticketDto;
    }

    public TicketEntity convert(TicketDto ticketDto) {
        if (ticketDto == null) {
            return null;
        }
        TicketEntity ticketEntity = modelMapper.map(ticketDto, TicketEntity.class);
        return ticketEntity;
    }

    public TicketDeleteResponse createDeleteResponse(String publicId) {
        TicketDeleteResponse ticketDeleteResponse = new TicketDeleteResponse();
        ticketDeleteResponse.setPublicId(publicId);
        ticketDeleteResponse.setStatus("user with publicId {" + publicId + "} deleted");
        return ticketDeleteResponse;
    }

    public TicketEntity update(TicketEntity existedTicketEntity, TicketRequest ticketRequest) {
        if (ticketRequest.getTitle() != null)
            existedTicketEntity.setTitle(ticketRequest.getTitle());
        if (ticketRequest.getDescription() != null)
            existedTicketEntity.setDescription(ticketRequest.getDescription());
        if (ticketRequest.getStatus() != null)
            existedTicketEntity.setStatus(ticketRequest.getStatus());
        return existedTicketEntity;
    }

    private boolean isValidRequestModel(TicketRequest ticketRequest) {
        boolean flag = true;
        if (ticketRequest == null) {
            return false;
        }
        if (ticketRequest.getStatus() == null) {
            return false;
        }
        if (ticketRequest.getDescription().isEmpty()) {
            return false;
        }
        if (ticketRequest.getTitle().isEmpty()) {
            return false;
        }
        return flag;
    }

}
