package rastak.train.ws.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class Login {
    @Schema(description = "Username that has already registered", example = "Alirez")
    private String username;
    @Schema(description = "password for entred username", example = "12@alireza#21")
    private String password;
}
