package pfko.vopalensky.spring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pfko.vopalensky.spring.response.UserResponse;
import pfko.vopalensky.spring.model.User;
import pfko.vopalensky.spring.service.UserService;

@RestController
public class UserApiController {
    private final UserService userService;


    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create user
     *
     * @param user Created user object
     * @return Newly created user
     */
    @PostMapping(value = "/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserResponse> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    /**
     * Log user into the system
     *
     * @param username The username for loginUser
     * @param password The password for loginUser
     */
    @GetMapping(value = "/login")
    public ResponseEntity<Void> loginUser(String username, String password) {
        return userService.loginUser(username, password);
    }

    /**
     * Logs out current logged-in user session
     */
    @GetMapping(value = "/logout")
    public ResponseEntity<Void> logoutUser() {
        return userService.logoutUser();
    }
}
