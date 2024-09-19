package pfko.vopalensky.spring.security;

import org.springframework.stereotype.Service;

@Service
public class UserController {
    public void resolveLogin(String username, String password) {
        System.out.println("Logged in...");
    }

    public void resolveLogout() {
        System.out.println("Logged out...");
    }
}
