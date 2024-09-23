package pfko.vopalensky.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfko.vopalensky.spring.model.User;
import pfko.vopalensky.spring.repository.UserRepository;
import pfko.vopalensky.spring.response.UserResponse;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getUserResponse(User user) {
        return new UserResponse(
                user.getId(), user.getUserName(),
                user.getStatus(), user.getName()
        );
    }

    public UserResponse getUserResponse(Long id) {
        return getUserResponse(userRepository.get(id));
    }
}
