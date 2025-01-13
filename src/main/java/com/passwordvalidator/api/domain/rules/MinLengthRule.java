package com.passwordvalidator.api.domain.rules;

import com.passwordvalidator.api.domain.PasswordRule;

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