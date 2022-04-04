package online.banking.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class AuthRequest {
    @JsonProperty
    private String username;

    @JsonProperty
    private String password;
}
