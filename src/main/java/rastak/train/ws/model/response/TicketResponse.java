package rastak.train.ws.model.response;

import jdk.jfr.DataAmount;
import lombok.Data;
import rastak.train.ws.model.entity.UserEntity;
import rastak.train.ws.model.enums.Status;

@Data
public class TicketResponse {
    private String publicId;
    private String title;
    private String Description;
    private Status status;
}
