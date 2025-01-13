package com.passwordvalidator.api.service;

import com.passwordvalidator.api.domain.PasswordRule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordValidatorService {

    private final List<PasswordRule> rules;

    public PasswordValidatorService(List<PasswordRule> rules) {
        this.rules = rules;
    }

    public boolean isValid(String password) {
        if (password == null || password.length() < 9) return false;

        for (PasswordRule rule : rules) {
            if (!rule.validate(password)) {
                System.out.println("Failed Rule: " + rule.getClass().getSimpleName());
                return false;
            }
        }
        return true;
    }
}
