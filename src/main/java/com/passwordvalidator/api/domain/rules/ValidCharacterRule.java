package com.passwordvalidator.api.domain.rules;

import com.passwordvalidator.api.domain.PasswordRule;

import java.util.Set;

public class ValidCharacterRule implements PasswordRule {
    private final Set<Character> validCharacters;

    public ValidCharacterRule(Set<Character> validCharacters) {
        this.validCharacters = validCharacters;
    }

    @Override
    public boolean validate(String password) {
        if (password == null || password.isEmpty()) return false;

        for (char c : password.toCharArray()) {
            if (!validCharacters.contains(c)) {
                return false;
            }
        }
        return true;
    }
}
