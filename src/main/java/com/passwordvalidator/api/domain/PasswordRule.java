package com.passwordvalidator.api.domain;

public interface PasswordRule {
    boolean validate(String password);
}