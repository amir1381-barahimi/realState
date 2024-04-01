package rastak.train.ws.service;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import rastak.train.shared.MyApiResponse;
import rastak.train.ws.model.dto.TicketDto;
import rastak.train.ws.model.entity.CommentEntity;
import rastak.train.ws.model.entity.UserEntity;
import rastak.train.ws.model.enums.Role;
import rastak.train.ws.model.request.TicketRequest;

import java.util.List;

public interface TicketService {

    ResponseEntity<MyApiResponse> getTicketByPublicId(String publicId);

    List<TicketDto> getAllTicket();

    ResponseEntity<MyApiResponse> addTicket(TicketRequest ticketRequest, HttpServletRequest request);

    ResponseEntity<MyApiResponse> updateTicket(String publicId, TicketRequest ticketRequest);

    ResponseEntity<MyApiResponse> deleteTicket(String publicId);

    List<TicketDto> findUserTicket(HttpServletRequest request);
    List<TicketDto> findTicketBySupport(Role role);
    void addComment(String TicketId, CommentEntity comment);
}
