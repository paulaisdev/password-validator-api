package com.passwordvalidator.api;

import javax.validation.constraints.NotBlank;

public class PasswordRequest {
    @NotBlank(message = "Password cannot be null or empty")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
