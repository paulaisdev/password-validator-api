package com.example.passwordvalidator.domain.rules;

import com.example.passwordvalidator.domain.PasswordRule;

public class MinLengthRule implements PasswordRule {
    private final int minLength;

    public MinLengthRule(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public boolean validate(String password) {
        return password != null && password.length() >= minLength;
    }
}