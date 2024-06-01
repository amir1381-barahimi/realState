package rastak.train.ws.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Data
public class DynamicForm {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String birthDate;
    private String gender;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String languages;
    private String educationLevel;
}