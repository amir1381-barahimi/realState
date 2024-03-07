package rastak.train.ws.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static rastak.train.ws.model.enums.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE
            )
    ),

    USER(Set.of(
            USER_READ
    ));

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorized() {
        var authorized = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorized.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorized;
    }
}





