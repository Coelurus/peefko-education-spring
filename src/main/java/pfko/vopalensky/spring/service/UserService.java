package pfko.vopalensky.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pfko.vopalensky.spring.error.exception.FieldValidationException;
import pfko.vopalensky.spring.model.User;
import pfko.vopalensky.spring.repository.UserRepository;
import pfko.vopalensky.spring.response.UserResponse;
import pfko.vopalensky.spring.security.UserController;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserController userController;

    @Autowired
    public UserService(UserRepository userRepository, UserController userController) {
        this.userRepository = userRepository;
        this.userController = userController;
    }

    /**
     * Create new user
     *
     * @param user User info to save in db
     * @return Response object of newly created object
     */
    public ResponseEntity<UserResponse> createUser(User user) {
        try {
            userRepository.store(user);
            return new ResponseEntity<>(new UserResponse(user), HttpStatus.OK);
        } catch (Exception e) {
            throw new FieldValidationException("Order");
        }
    }

    /**
     * Login user
     *
     * @param username Username of user
     * @param password Password of user
     * @return response
     */
    public ResponseEntity<Void> loginUser(String username, String password) {
        userController.resolveLogin(username, password);
        return ResponseEntity.ok().build();
    }

    /**
     * Resolves logout of user
     *
     * @return response
     */
    public ResponseEntity<Void> logoutUser() {
        userController.resolveLogout();
        return ResponseEntity.ok().build();
    }
}
