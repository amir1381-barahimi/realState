package com.realstate.demo.ws.repository;

import com.realstate.demo.ws.model.entity.TicketEntity;
import com.realstate.demo.ws.model.entity.UserEntity;
import com.realstate.demo.ws.model.enums.Role;
import com.realstate.demo.ws.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    TicketEntity findByPublicId(String publicId);
    List<TicketEntity> findAll();
    int deleteByPublicId(String publicId);
    List<TicketEntity> findByUser(UserEntity user);
    int countBySupportAndStatus(UserEntity user, Status status);

    List<TicketEntity> findBySupport(Role role);
}
