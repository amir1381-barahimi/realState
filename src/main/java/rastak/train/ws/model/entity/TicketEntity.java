package rastak.train.ws.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import rastak.train.ws.model.enums.Status;

import java.util.List;

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
