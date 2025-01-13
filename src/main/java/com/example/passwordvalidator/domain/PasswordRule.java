package com.example.passwordvalidator.domain;

public interface PasswordRule {
    boolean validate(String password);
}