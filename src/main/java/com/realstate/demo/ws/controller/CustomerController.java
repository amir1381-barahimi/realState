//package com.realstate.demo.ws.controller;
//
//import com.realstate.demo.shared.MyApiResponse;
//import com.realstate.demo.ws.model.dto.TicketDto;
//import com.realstate.demo.ws.model.request.TicketRequest;
//import com.realstate.demo.ws.model.response.TicketResponse;
//import com.realstate.demo.ws.service.TicketService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import jakarta.servlet.http.HttpServletRequest;
//import org.modelmapper.ModelMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/customer")
//@PreAuthorize("hasRole('CUSTOMER')")
//public class CustomerController {
//    private final TicketService ticketService;
//    Logger logger = LoggerFactory.getLogger(UserController.class);
//
//    public CustomerController(TicketService ticketService) {
//        this.ticketService = ticketService;
//    }
//
//    @Operation(summary = "get all user tickets", description = "Get All Ticket, customer can access to this method ", tags = {"CUSTOMER-TICKETS"})
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "The request succeeded.", content = {@Content(mediaType = "application/json")}),
//            @ApiResponse(responseCode = "404", description = "The server cannot find the requested resource. In the browser, this means the URL is not recognized.", content = {@Content(mediaType = "application/json")}),
//            @ApiResponse(responseCode = "500", description = "The server has encountered a situation it does not know how to handle.", content = {@Content(mediaType = "application/json")})
//    })
//    @GetMapping("tickets/my-tickets")
//    @PreAuthorize("hasAuthority('customer:read')")
//    public List<TicketResponse> getMyTickets(HttpServletRequest request) {
//        List<TicketDto> ticketDtos = ticketService.findUserTicket(request);
//        return ticketDtos.stream().map(ticketDto -> new ModelMapper().map(ticketDto, TicketResponse.class)).toList();
//    }
//
//    @Operation(summary = "update customer ticket", description = "update ticket By Id from Database, Only customer can access to this method", tags = {"CUSTOMER-TICKETS"})
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "The request succeeded.", content = {@Content(mediaType = "application/json")}),
//            @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request due to something that is perceived to be a client error (e.g., malformed request syntax, invalid request message framing, or deceptive request routing", content = {@Content(mediaType = "application/json")}),
//            @ApiResponse(responseCode = "500", description = "The server has encountered a situation it does not know how to handle.", content = {@Content(mediaType = "application/json")})
//    })
//    @PutMapping("/{publicId}")
//    @PreAuthorize("hasAuthority('customer:update')")
//    public ResponseEntity<MyApiResponse> updateTicket(@RequestBody TicketRequest ticketRequest, @PathVariable String publicId) {
//        return ticketService.updateTicket(publicId, ticketRequest);
//    }
//    @Operation(summary = "Create ticket", description = "Add new ticket to database", tags = {"CUSTOMER-TICKETS"})
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "The request succeeded, and a new resource was created as a result.", content = {@Content(mediaType = "application/json")}),
//            @ApiResponse(responseCode = "404", description = "The server cannot find the requested resource. In the browser, this means the URL is not recognized.", content = {@Content(mediaType = "application/json")}),
//            @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request due to something that is perceived to be a client error (e.g., malformed request syntax, invalid request message framing, or deceptive request routing", content = {@Content(mediaType = "application/json")}),
//            @ApiResponse(responseCode = "500", description = "The server has encountered a situation it does not know how to handle.", content = {@Content(mediaType = "application/json")}),
//    })
//    @PostMapping
//    @PreAuthorize("hasAuthority('customer:create')")
//    public ResponseEntity<MyApiResponse> addTickets(@RequestBody TicketRequest ticketRequest, HttpServletRequest request) {
//        logger.info("add new ticket to database  with title: {}", ticketRequest.getTitle());
//        return ticketService.addTicket(ticketRequest, request);
//    }
//}
