package rastak.train.ws.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rastak.train.ws.model.enums.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUp {
    private String username;
    private String password;
    private String fullname;
    private UserRole userRole;
}
