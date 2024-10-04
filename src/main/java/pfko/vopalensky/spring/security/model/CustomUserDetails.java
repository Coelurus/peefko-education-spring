package pfko.vopalensky.spring.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(role)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}