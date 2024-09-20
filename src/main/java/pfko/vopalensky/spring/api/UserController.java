package pfko.vopalensky.spring.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
