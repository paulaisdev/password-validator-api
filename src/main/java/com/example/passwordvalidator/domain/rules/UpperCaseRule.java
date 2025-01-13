package com.example.passwordvalidator.domain.rules;

import com.example.passwordvalidator.domain.PasswordRule;

public class UpperCaseRule implements PasswordRule {

    @Override
    public boolean validate(String password) {
        return password != null && password.chars().anyMatch(Character::isUpperCase);
    }
}