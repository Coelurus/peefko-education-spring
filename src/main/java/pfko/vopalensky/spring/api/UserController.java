package pfko.vopalensky.spring.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Redirecting for login and logout.
 * It is easier to have .html files than test
 * everything through Invoke-WebRequest sue me
 */
@Controller
public class UserController {
    @GetMapping(value = "/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping(value = "/")
    public String showLogoutPage() {
        return "hello";
    }
}
