package rastak.train.ws.model.request;

import lombok.Data;
import rastak.train.ws.model.enums.Status;
@Data
public class TicketRequest {

    private String title;
    private String Description;
    private Status status;
}
