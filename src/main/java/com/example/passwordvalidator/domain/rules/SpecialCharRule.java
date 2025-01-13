package com.example.passwordvalidator.domain.rules;

import com.example.passwordvalidator.domain.PasswordRule;

import java.util.Set;

public class SpecialCharRule implements PasswordRule {
    private final Set<Character> specialChars;

    public SpecialCharRule(Set<Character> specialChars) {
        this.specialChars = specialChars;
    }

    @Override
    public boolean validate(String password) {
        return password != null && password.chars().anyMatch(c -> specialChars.contains((char) c));
    }
}