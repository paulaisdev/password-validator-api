package com.passwordvalidator.api.domain.rules;

import com.passwordvalidator.api.domain.PasswordRule;

public class UpperCaseRule implements PasswordRule {

    @Override
    public boolean validate(String password) {
        return password != null && password.chars().anyMatch(Character::isUpperCase);
    }
}