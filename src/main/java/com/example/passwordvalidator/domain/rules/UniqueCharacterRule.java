package com.example.passwordvalidator.domain.rules;

import com.example.passwordvalidator.domain.PasswordRule;

import java.util.HashSet;
import java.util.Set;

public class UniqueCharacterRule implements PasswordRule {
    @Override
    public boolean validate(String password) {
        if (password == null || password.isEmpty()) return false;

        Set<Character> seen = new HashSet<>();
        for (char c : password.toCharArray()) {
            if (!seen.add(c)) {
                return false;
            }
        }
        return true;
    }
}
