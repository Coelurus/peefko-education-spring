package pfko.vopalensky.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import pfko.vopalensky.spring.model.Status;
import pfko.vopalensky.spring.repository.UserRepository;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserRepository userRepository;
    private final AuthenticationEntryPoint authEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public WebSecurityConfig(UserRepository userRepository,
                             AuthenticationEntryPoint authEntryPoint,
                             AccessDeniedHandler accessDeniedHandler) {
        this.userRepository = userRepository;
        this.authEntryPoint = authEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(antMatcher(HttpMethod.GET, "/offer")).permitAll()
                        .requestMatchers("/offer").hasRole(Status.SUPPLIER.toString())
                        .requestMatchers("/order/my").authenticated()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/order")).authenticated()
                        .requestMatchers("/order").hasRole(Status.SUPPLIER.toString())
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        List<UserDetails> users = new java.util.ArrayList<>(userRepository
                .findAll()
                .stream()
                .map(u -> User.withUsername(u.getUserName())
                        .password(u.getPassword())
                        .roles(u.getStatus().toString())
                        .build()).toList());
        
        return new InMemoryUserDetailsManager(users);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
