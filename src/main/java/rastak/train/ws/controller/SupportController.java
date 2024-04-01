package rastak.train.ws.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rastak.train.ws.model.dto.TicketDto;
import rastak.train.ws.model.response.TicketResponse;
import rastak.train.ws.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/support")
@PreAuthorize("hasRole('SUPPORT')")
public class SupportController {
    private final TicketService ticketService;

    public SupportController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    @Operation(summary = "get all tickets", description = "Get All Ticket for current support user, SUPPORT can access to this method ", tags = {"SUPPORT-TICKETS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The request succeeded.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "The server cannot find the requested resource. In the browser, this means the URL is not recognized.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "The server has encountered a situation it does not know how to handle.", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/tickets")
    @PreAuthorize("hasAuthority('support:read')")
    public List<TicketResponse> getAllTicket() {
        List<TicketDto> ticketDtos = ticketService.getAllTicket();
        return ticketDtos.stream().map(ticketDto -> new ModelMapper().map(ticketDto, TicketResponse.class)).toList();
    }
}
