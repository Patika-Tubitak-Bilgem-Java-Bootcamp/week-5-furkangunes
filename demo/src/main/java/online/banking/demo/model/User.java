package online.banking.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class User implements Persistable {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String token;
    private LocalDateTime tokenExpiration;
    private List<String> accounts;

    public User(final JSONObject jsonObject) {
        username = (String) jsonObject.get("username");
        password = (String) jsonObject.get("password");
        firstName = (String) jsonObject.get("firstName");
        lastName = (String) jsonObject.get("lastName");
        token = (String) jsonObject.get("token");
        tokenExpiration = LocalDateTime.parse((String) jsonObject.get("tokenExpiration"));
        accounts = (List<String>) jsonObject.get("accounts");
    }

    public boolean isTokenExpired() {
        return tokenExpiration == null || LocalDateTime.now().isAfter(tokenExpiration);
    }

    @Override
    public String getId() {
        return username;
    }

    @Override
    public String getIdKeyword() {
        return "username";
    }

    @Override
    public JSONObject dump() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        jsonObject.put("firstName", firstName);
        jsonObject.put("lastName", lastName);
        jsonObject.put("token", token);
        jsonObject.put("tokenExpiration", tokenExpiration.toString());
        jsonObject.put("accounts", accounts);

        return jsonObject;
    }
}
