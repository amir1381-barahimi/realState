package com.realstate.demo.ws.service;

import com.realstate.demo.shared.MyApiResponse;
import com.realstate.demo.ws.model.dto.TicketDto;
import com.realstate.demo.ws.model.enums.Role;
import com.realstate.demo.ws.model.request.TicketRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TicketService {

    ResponseEntity<MyApiResponse> getTicketByPublicId(String publicId);

    List<TicketDto> getAllTicket();

    ResponseEntity<MyApiResponse> addTicket(TicketRequest ticketRequest, HttpServletRequest request);

    ResponseEntity<MyApiResponse> updateTicket(String publicId, TicketRequest ticketRequest);

    ResponseEntity<MyApiResponse> deleteTicket(String publicId);

    List<TicketDto> findUserTicket(HttpServletRequest request);
    List<TicketDto> findTicketBySupport(Role role);
//    void addComment(String TicketId, CommentEntity comment);
}
