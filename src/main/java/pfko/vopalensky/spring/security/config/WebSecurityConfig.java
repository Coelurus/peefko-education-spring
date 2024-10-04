package pfko.vopalensky.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
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
import pfko.vopalensky.spring.model.Role;
import pfko.vopalensky.spring.repository.UserRepository;
import pfko.vopalensky.spring.security.service.CustomUserDetailsService;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserRepository userRepository;
    private final AuthenticationEntryPoint authEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(UserRepository userRepository,
                             AuthenticationEntryPoint authEntryPoint,
                             AccessDeniedHandler accessDeniedHandler,
                             CustomUserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.authEntryPoint = authEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(antMatcher(HttpMethod.GET, "/offer")).permitAll()
                        .requestMatchers("/offer").hasRole(Role.SUPPLIER.toString())
                        .requestMatchers("/order/my").authenticated()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/order")).authenticated()
                        .requestMatchers("/order").hasRole(Role.SUPPLIER.toString())
                        .anyRequest().authenticated()
                )
                .authenticationManager(authenticationManager())
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler));

        return http.build();
    }

    private AuthenticationManager authenticationManager() {
        AuthenticationManager authenticationManager
                = new AuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder());
        return authenticationManager;
    }

    @Bean
    public UserDetailsService userDetailsService() {

        List<UserDetails> users = new java.util.ArrayList<>(userRepository
                .findAll()
                .stream()
                .map(u -> User.withUsername(u.getUserName())
                        .password(u.getPassword())
                        .roles(u.getRole().toString())
                        .build()).toList());

        return new InMemoryUserDetailsManager(users);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
