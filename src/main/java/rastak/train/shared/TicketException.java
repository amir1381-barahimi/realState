package rastak.train.shared;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class TicketException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;
    public TicketException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
