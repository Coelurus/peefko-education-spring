package pfko.vopalensky.spring.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pfko.vopalensky.spring.model.Status;
import pfko.vopalensky.spring.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserRepository implements ObjectRepository<User> {

    private final List<User> users = new ArrayList<>();

    @Override
    public void store(User user) {
        users.add(user);
    }

    @Override
    public User get(Long id) {
        return users.stream()
                .filter(u -> Objects.equals(u.getId(), id))
                .findFirst().orElse(null);
    }

    public User get(String username) {
        return users.stream()
                .filter(u -> Objects.equals(u.getUserName(), username))
                .findFirst().orElse(null);
    }

    @Override
    public User delete(Long id) {
        User user = get(id);
        users.removeIf(u -> Objects.equals(u.getId(), id));
        return user;
    }

    @Override
    public List<User> findAll() {
        return users;
    }

}
