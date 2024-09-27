package pfko.vopalensky.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pfko.vopalensky.spring.error.exception.AuthenticationException;
import pfko.vopalensky.spring.model.Status;
import pfko.vopalensky.spring.model.User;
import pfko.vopalensky.spring.repository.UserRepository;
import pfko.vopalensky.spring.response.UserResponse;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Returns user as user responses
     *
     * @param user User to transform to response
     * @return User response
     */
    public UserResponse getUserResponse(User user) {
        return new UserResponse(
                user.getId(), user.getUserName(),
                user.getStatus(), user.getName()
        );
    }

    /**
     * Returns user by id as user responses
     *
     * @param id ID of user to transform to response
     * @return User response
     */
    public UserResponse getUserResponse(Long id) {
        return getUserResponse(userRepository.get(id));
    }

    /**
     * Get username of currently logged-in user
     *
     * @return Name of user
     */
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationException("Anonymous user");
        }

        return authentication.getName();
    }

    /**
     * Check whether currently logged-in user has this username
     *
     * @param username Username to check if logged-in user has it
     * @return True if parameter username is logged-in user's username
     */
    public boolean isCurrentlyLoggedIn(String username) {
        return Objects.equals(getCurrentUsername(), username);
    }

    /**
     * Check whether currently logged-in user has this id
     *
     * @param userId User ID to check if logged-in user has it
     * @return True if parameter userId is logged-in user's id
     */
    public boolean isCurrentlyLoggedIn(Long userId) {
        User loggedInUser = userRepository.findAll().stream()
                .filter(user -> Objects.equals(user.getId(), userId))
                .findFirst().orElseThrow(() -> new AuthenticationException("User not found"));
        return isCurrentlyLoggedIn(loggedInUser.getUserName());
    }

    /**
     * Check whether currently logged-in user has supplier role
     *
     * @return True if logged-in user is supplier
     */
    public boolean isThisSupplier() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationException("Anonymous user");
        }

        return authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(Status.SUPPLIER.name()));
    }
}
