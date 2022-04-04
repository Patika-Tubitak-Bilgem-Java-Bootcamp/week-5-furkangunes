package online.banking.demo;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import online.banking.demo.dto.AuthRequest;
import online.banking.demo.util.DoesNotExistException;

@RestController
public class AuthController {
    public static final AuthManager authManager = new AuthManager();

    @PostMapping("/auth")
    public String auth(@RequestBody AuthRequest authRequest) {
        try {
            return authManager.getToken(authRequest.getUsername(), authRequest.getPassword());
        } catch (DoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find user");
        }
    }
}
