package rastak.train.ws.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import rastak.train.ws.model.enums.UserRole;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 50, nullable = false)
    private String publicId;

    @Column(length = 50)
    @NotNull
    @NotBlank
    private String fullname;

    @Column(length = 50)
    @NotNull
    @NotBlank
    private String username;

    @Column(length = 50)
    @NotNull
    @NotBlank
    private String password;

    @NotNull
    private UserRole userRole;
}
