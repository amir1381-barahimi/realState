package rastak.train.ws.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rastak.train.token.Token;
import rastak.train.ws.model.enums.Role;

import java.util.Collection;
import java.util.List;

@Data
@Entity
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 50, nullable = false)
    private String publicId;

    @Column(length = 50)
    private String fullname;

    @Column(length = 50)
    private String email;

    @Column(length = 50)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorized();
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Token> tokens;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
