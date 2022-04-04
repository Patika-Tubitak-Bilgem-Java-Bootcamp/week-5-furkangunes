package online.banking.demo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import online.banking.demo.model.User;

@Getter
public class UserDto {
    @JsonProperty
    private String firstName;
    
    @JsonProperty
    private String lastName;
    
    @JsonProperty
    private List<String> accounts;

    public UserDto(final User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.accounts = user.getAccounts();
    }
}
