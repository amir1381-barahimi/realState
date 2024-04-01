package rastak.train.ws.model.dto;

import lombok.Data;
import rastak.train.ws.model.entity.UserEntity;
import rastak.train.ws.model.enums.Status;

@Data
public class TicketDto {

    private long id;
    private String publicId;
    private String title;
    private String Description;
    private Status status;
    private UserEntity user;
    private UserEntity support;
}
