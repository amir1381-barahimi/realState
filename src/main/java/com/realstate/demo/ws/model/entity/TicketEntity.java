package com.realstate.demo.ws.model.entity;

import com.realstate.demo.ws.model.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String publicId;
    private String title;
    private String Description;
    private Status status;
//    @OneToMany
//    private List<CommentEntity> comment;
    @ManyToOne
    private UserEntity support;
    @ManyToOne
    private UserEntity user;
}
