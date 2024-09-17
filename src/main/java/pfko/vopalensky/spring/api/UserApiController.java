package pfko.vopalensky.spring.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pfko.vopalensky.spring.security.UserController;
import pfko.vopalensky.spring.model.User;
import pfko.vopalensky.spring.repository.UserRepository;

@RestController
public class UserApiController implements UserApi {
    private final UserController userController;
    private final UserRepository userRepository;
    private final HttpServletRequest request;


    @Autowired
    public UserApiController(UserController userController,
                             UserRepository userRepository,
                             HttpServletRequest request, HttpSession httpSession) {
        this.userController = userController;
        this.userRepository = userRepository;
        this.request = request;
    }

    @Override
    public ResponseEntity<User> createUser(User user) {
        return Helper.objectCreator(user, request, userRepository);
    }

    @Override
    public ResponseEntity<Void> loginUser(String username, String password) {
        userController.resolveLogin(username, password);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> logoutUser() {
        userController.resolveLogout();
        return ResponseEntity.ok().build();
    }
}
