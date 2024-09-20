package pfko.vopalensky.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pfko.vopalensky.spring.error.exception.FieldValidationException;
import pfko.vopalensky.spring.model.LoginRequest;
import pfko.vopalensky.spring.model.UserM;
import pfko.vopalensky.spring.repository.UserRepository;

@Service
public class UserService {
    private static final String SCOPE = "User";

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Create new user
     *
     * @param user User info to save in db
     * @return Response status
     */
    public ResponseEntity<Void> createUser(UserM user) {
        try {
            userRepository.store(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new FieldValidationException(SCOPE);
        }
    }

    /**
     * Login user
     *
     * @param loginRequest Username and password of user
     * @return response
     */
    public ResponseEntity<Void> loginUser(LoginRequest loginRequest) {

        return ResponseEntity.ok().build();
    }

    /**
     * Resolves logout of user
     *
     * @return response
     */
    public ResponseEntity<Void> logoutUser() {

        return ResponseEntity.internalServerError().build();
    }
}
