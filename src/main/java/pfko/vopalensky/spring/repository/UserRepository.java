package pfko.vopalensky.spring.repository;

import org.springframework.stereotype.Repository;
import pfko.vopalensky.spring.model.StatusEnum;
import pfko.vopalensky.spring.model.UserM;
import pfko.vopalensky.spring.response.UserResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserRepository implements ObjectRepository<UserM> {

    private final List<UserM> users = new ArrayList<>();

    public UserRepository() {
        users.add(new UserM(0L, "user", "user",
                StatusEnum.CUSTOMER, "Mardi"));
        users.add(new UserM(1L, "admin", "admin",
                StatusEnum.SUPPLIER, "TJ"));
    }

    @Override
    public void store(UserM user) {
        users.add(user);
    }

    @Override
    public UserM get(Long id) {
        return users.stream()
                .filter(u -> Objects.equals(u.getId(), id))
                .findFirst().orElse(null);
    }

    public UserM get(String username) {
        return users.stream()
                .filter(u -> Objects.equals(u.getUserName(), username))
                .findFirst().orElse(null);
    }

    @Override
    public UserM delete(Long id) {
        UserM user = get(id);
        users.removeIf(u -> Objects.equals(u.getId(), id));
        return user;
    }

    @Override
    public List<UserM> findAll() {
        return users;
    }

    public List<UserResponse> getResponses() {
        return users.stream().map(UserResponse::new).toList();
    }
}
