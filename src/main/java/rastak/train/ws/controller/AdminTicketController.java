package rastak.train.ws.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rastak.train.shared.MyApiResponse;
import rastak.train.ws.model.dto.TicketDto;
import rastak.train.ws.model.request.TicketRequest;
import rastak.train.ws.model.response.TicketResponse;
import rastak.train.ws.service.TicketService;
import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER') or hasRole('SUPPORT')")
public class AdminTicketController {

    private final TicketService ticketService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public AdminTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Operation(summary = "get a ticket", description = "Get ticket By PublicId, ADMIN can access to this method", tags = {"ADMIN-TICKETS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The request succeeded.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "The server cannot find the requested resource. In the browser, this means the URL is not recognized.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "The server has encountered a situation it does not know how to handle.", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/tickets/{publicId}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<MyApiResponse> getTicketById(@PathVariable String publicId) {
        return ticketService.getTicketByPublicId(publicId);
    }

    @Operation(summary = "get all tickets", description = "Get All Ticket, ADMIN can access to this method ", tags = {"ADMIN-TICKETS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The request succeeded.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "The server cannot find the requested resource. In the browser, this means the URL is not recognized.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "The server has encountered a situation it does not know how to handle.", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/tickets")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('customer:read')")
    public List<TicketResponse> getAllTicket() {
        List<TicketDto> ticketDtos = ticketService.getAllTicket();
        return ticketDtos.stream().map(ticketDto -> new ModelMapper().map(ticketDto, TicketResponse.class)).toList();
    }


    @Operation(summary = "update ticket", description = "update ticket By Id from Database, Only ADMIN can access to this method", tags = {"ADMIN-TICKETS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The request succeeded.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request due to something that is perceived to be a client error (e.g., malformed request syntax, invalid request message framing, or deceptive request routing", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "The server has encountered a situation it does not know how to handle.", content = {@Content(mediaType = "application/json")})
    })
    @PutMapping("/tickets/{publicId}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<MyApiResponse> updateTicket(@RequestBody TicketRequest ticketRequest, @PathVariable String publicId) {
        return ticketService.updateTicket(publicId, ticketRequest);
    }

    @Operation(summary = "delete ticket", description = "delete ticket By Id from Database, Only ADMIN can access to this method", tags = {"ADMIN-TICKETS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The request succeeded.", content = {@Content(mediaType = "application/json", schema = @Schema(type = "object"))}),
            @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request due to something that is perceived to be a client error (e.g., malformed request syntax, invalid request message framing, or deceptive request routing).", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = """
                    The server has encountered a situation it does not know how to handle.
                    """, content = {@Content(mediaType = "application/json")})
    })
    @DeleteMapping("/tickets/{publicId}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Transactional
    public ResponseEntity<MyApiResponse> deleteTickets(@PathVariable String publicId) {
        logger.info("delete user by public: {}", publicId);
        return ticketService.deleteTicket(publicId);
    }

}
