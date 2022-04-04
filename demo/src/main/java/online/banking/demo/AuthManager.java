package online.banking.demo;

import java.io.IOException;
import java.io.Writer;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ListIterator;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.nio.file.Path;

import online.banking.demo.model.User;
import online.banking.demo.util.DoesNotExistException;
import online.banking.demo.util.TokenExpiredException;

public class AuthManager extends PersistanceManager {
    private static final int TOKEN_DURATION = 2; // hours
    // private static final Path USER_FILE_PATH = Paths.get(
    //     "src", "main", "resources", "data", "User.json"
    // );

    private static final Path USER_FILE_PATH = Paths.get(BASE_FILE_PATH.toString(), "User.json");
    
    public String getToken(final String username, final String password) throws DoesNotExistException {
        try {
            JSONObject jsonObject = findObject(username, "username", USER_FILE_PATH);
            if (!password.equals(jsonObject.get("password"))) {
                throw new DoesNotExistException();
            }

            User user = new User(jsonObject);
            if (user.isTokenExpired()) {
                user.setToken(generateToken());
                user.setTokenExpiration(LocalDateTime.now().plusHours(TOKEN_DURATION));
                persist(USER_FILE_PATH, user);
            }
            return user.getToken();
        } catch (IOException | ParseException e) {
            return null;
        }
    }

    public User getUserByToken(final String token) throws DoesNotExistException, TokenExpiredException {
        try {
            JSONObject jsonObject = findObject(token, "token", USER_FILE_PATH);
            User user = new User(jsonObject);

            if (user.isTokenExpired()) {
                throw new TokenExpiredException();
            }

            return user;
        } catch (IOException | ParseException e) {
            return null;
        }
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
