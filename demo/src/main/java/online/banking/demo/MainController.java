package online.banking.demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@Controller
@EnableAutoConfiguration
@ComponentScan
public class MainController {
    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/auth")
    public String auth() {
        return "auth/auth.html";
    }

    @GetMapping("/home")
    public String home() {
        return "home/home.html";
    }
}
