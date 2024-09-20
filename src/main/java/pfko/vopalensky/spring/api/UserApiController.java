package pfko.vopalensky.spring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pfko.vopalensky.spring.model.LoginRequest;
import pfko.vopalensky.spring.model.UserM;
import pfko.vopalensky.spring.service.UserService;

@RestController
public class UserApiController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;


    @Autowired
    public UserApiController(UserService userService, @Qualifier("userDetailsService") UserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Create user
     *
     * @param user Created user object
     * @return Response status
     */
    @PostMapping(value = "/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> createUser(@RequestBody UserM user) {
        return userService.createUser(user);
    }

    /**
     * Log user into the system
     *
     * @param loginRequest Object with username and password to log-in user
     */
    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<Void> loginUser(@RequestBody(required = false) LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

    /**
     * Logs out current logged-in user session
     */
    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logoutUser() {
        return userService.logoutUser();
    }
}
