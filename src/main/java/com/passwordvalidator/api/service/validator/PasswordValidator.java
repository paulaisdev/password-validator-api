package com.passwordvalidator.api.service.validator;

import com.passwordvalidator.api.exception.PasswordValidationException;
import com.passwordvalidator.api.util.InputSanitizer;

import java.util.HashSet;
import java.util.Set;

public class PasswordValidator {

    private static final int MAX_PASSWORD_LENGTH = 100;

    public static void validate(String password) {
        String sanitizedPassword = InputSanitizer.sanitize(password);

        if (sanitizedPassword == null || sanitizedPassword.isEmpty()) {
            throw new PasswordValidationException("Password is null or empty");
        }

        if (sanitizedPassword.length() > MAX_PASSWORD_LENGTH) {
            throw new PasswordValidationException("Password exceeds maximum length of " + MAX_PASSWORD_LENGTH);
        }

        if (!containsOnlyValidCharacters(sanitizedPassword)) {
            throw new PasswordValidationException("Password contains invalid characters");
        }

        if (containsRepeatedCharacters(sanitizedPassword)) {
            throw new PasswordValidationException("Password contains repeated characters");
        }
    }

    private static boolean containsOnlyValidCharacters(String password) {
        return password.matches("^[a-zA-Z0-9!@#$%^&*()\\-+]*$");
    }

    private static boolean containsRepeatedCharacters(String password) {
        Set<Character> seen = new HashSet<>();
        for (char c : password.toCharArray()) {
            if (!seen.add(c)) {
                return true;
            }
        }
        return false;
    }
}
