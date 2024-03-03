package rastak.train.shared;

import lombok.Data;

import java.util.Date;

@Data
public class MyApiResponse {
    private boolean action;
    private String message;
    private Date date;
    private Object result;
}
