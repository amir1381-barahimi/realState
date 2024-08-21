package com.realstate.demo.ws.service.impl;

import com.realstate.demo.shared.MyApiResponse;
import com.realstate.demo.shared.UserException;
import com.realstate.demo.ws.model.dto.TicketDto;
import com.realstate.demo.ws.model.entity.TicketEntity;
import com.realstate.demo.ws.model.entity.UserEntity;
import com.realstate.demo.ws.model.enums.Role;
import com.realstate.demo.ws.model.request.TicketRequest;
import com.realstate.demo.ws.model.response.TicketDeleteResponse;
import com.realstate.demo.ws.model.response.TicketResponse;
import com.realstate.demo.ws.repository.TicketRepository;
import com.realstate.demo.ws.service.TicketService;
import com.realstate.demo.ws.util.TicketUtils;
import com.realstate.demo.ws.util.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final UserUtils userUtils;
    private final TicketRepository ticketRepository;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final TicketUtils ticketUtils;

    public TicketServiceImpl(UserUtils userUtils, TicketRepository ticketRepository, TicketUtils ticketUtils) {
        this.userUtils = userUtils;
        this.ticketRepository = ticketRepository;
        this.ticketUtils = ticketUtils;
    }

    @Override
    public ResponseEntity<MyApiResponse> getTicketByPublicId(String publicId) {
        TicketEntity ticketEntity = ticketRepository.findByPublicId(publicId);
        if (ticketEntity == null) {
            throw new UserException("Ticket not found", HttpStatus.NOT_FOUND);
        }
        TicketResponse ticketResponse = ticketUtils.convert(ticketEntity);
        return ticketUtils.createResponse(ticketResponse, HttpStatus.OK);
    }


    @Override
    public List<TicketDto> findUserTicket(HttpServletRequest request) {
        UserEntity currentUser = userUtils.getCurrentUser(request);
        List<TicketEntity> ticket = ticketRepository.findByUser(currentUser);
        if (ticket == null) {
            throw new UserException("Any ticket not found", HttpStatus.NOT_FOUND);
        }
        return ticket.stream().map(ticketEntity -> new ModelMapper().map(ticketEntity, TicketDto.class)).toList();
    }


    @Override
    public List<TicketDto> getAllTicket() {
        List<TicketEntity> ticketEntities = ticketRepository.findAll();
        if (ticketEntities == null) {
            throw new UserException("Any ticket not found", HttpStatus.NOT_FOUND);
        }
        return ticketEntities.stream().map(ticketEntity -> new ModelMapper().map(ticketEntity, TicketDto.class)).toList();
    }

    @Override
    public List<TicketDto> findTicketBySupport(Role role) {
        List<TicketEntity> ticketEntities = ticketRepository.findBySupport(role.SUPPORT);
        if (ticketEntities == null) {
            throw new UserException("Any ticket not found", HttpStatus.NOT_FOUND);
        }
        return ticketEntities.stream().map(ticketEntity -> new ModelMapper().map(ticketEntity, TicketDto.class)).toList();
    }

//    @Override
//    public void addComment(String TicketId, CommentEntity comment) {
//        TicketEntity ticketEntity = ticketRepository.findByPublicId(TicketId);
//
//    }

    @Override
    public ResponseEntity<MyApiResponse> addTicket(TicketRequest ticketRequest, HttpServletRequest request) {

        UserEntity currentUser = userUtils.getCurrentUser(request);

        UserEntity support = userUtils.findSupport();
        TicketDto ticketDto = ticketUtils.convert(ticketRequest);
        ticketDto.setSupport(support);
        ticketDto.setUser(currentUser);
        if (ticketDto == null) {
            throw new UserException("Invalid Input", HttpStatus.BAD_REQUEST);
        }
        TicketEntity ticketEntity = ticketUtils.convert(ticketDto);
        TicketEntity storedTicketEntity;
        try {
            storedTicketEntity = ticketRepository.save(ticketEntity);
        } catch (Exception exception) {
            logger.info(exception.getMessage());
            throw new UserException("DataBase IO error", HttpStatus.BAD_REQUEST);
        }
        return ticketUtils.createResponse(ticketUtils.convert(storedTicketEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MyApiResponse> updateTicket(String publicId, TicketRequest ticketRequest) {
        TicketEntity existedTicketEntity = ticketRepository.findByPublicId(publicId);
        if (existedTicketEntity == null) {
            throw new UserException("Ticket not found", HttpStatus.NOT_FOUND);
        }
        TicketEntity updateTicketEntity;
        updateTicketEntity = ticketUtils.update(existedTicketEntity, ticketRequest);
        if (updateTicketEntity == null) {
            throw new UserException("Invalid Input", HttpStatus.NOT_FOUND);
        }
        try {
            updateTicketEntity = ticketRepository.save(updateTicketEntity);
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            throw new UserException("Database IO Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ticketUtils.createResponse(ticketUtils.convert(updateTicketEntity), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MyApiResponse> deleteTicket(String publicId) {
        int deleteUser = ticketRepository.deleteByPublicId(publicId);
        if (deleteUser == 0) {
            throw new UserException("Ticket not found with this id", HttpStatus.NOT_FOUND);
        }
        TicketDeleteResponse ticketDeleteResponse = ticketUtils.createDeleteResponse(publicId);
        return ticketUtils.createResponse(ticketDeleteResponse, HttpStatus.OK);
    }

}


