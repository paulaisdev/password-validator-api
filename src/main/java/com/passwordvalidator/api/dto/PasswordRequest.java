package com.passwordvalidator.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PasswordRequest {

    @NotBlank(message = "Password must not be blank")
    @Size(max = 100, message = "Password exceeds maximum allowed length")
    private String password;

    public PasswordRequest() {}

    public PasswordRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
