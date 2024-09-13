package pfko.vopalensky.spring.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pfko.vopalensky.spring.model.User;

@RestController
public interface UserApi {

    /**
     * Create user
     *
     * @param user Created user object
     * @return Newly created user
     */
    @PostMapping(value = "/",
            produces = {"application/json", "application/xml"},
            consumes = {"application/json", "application/xml"})
    ResponseEntity<User> createUser(@RequestBody User user);

    /**
     * Log user into the system
     *
     * @param username The username for loginUser
     * @param password The password for loginUser
     */
    @GetMapping(value = "/login",
            produces = {"application/json", "application/xml"})
    ResponseEntity<Void> loginUser(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password
    );

    /**
     * Logs out current logged-in user session
     */
    @GetMapping(value = "/logout")
    ResponseEntity<Void> logoutUser();
}
