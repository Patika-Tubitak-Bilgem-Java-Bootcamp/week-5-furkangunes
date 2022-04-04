package online.banking.demo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import online.banking.demo.dto.UserDto;
import online.banking.demo.model.User;
import online.banking.demo.util.DoesNotExistException;
import online.banking.demo.util.TokenExpiredException;

@RestController
public class UserController {
    public static final AccountManager accountManager = new AccountManager();

    @GetMapping("/user")
    public UserDto accounts(@RequestHeader("token") String token) {
        try {
            User user = AuthController.authManager.getUserByToken(token);
            return new UserDto(user);
        } catch (DoesNotExistException | TokenExpiredException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
