package com.passwordvalidator.api.domain.rules;

import com.passwordvalidator.api.domain.PasswordRule;

import java.util.HashSet;
import java.util.Set;

public class UniqueCharacterRule implements PasswordRule {
    @Override
    public boolean validate(String password) {
        if (password == null || password.isEmpty()) return false;

        Set<Character> seen = new HashSet<>();
        for (char c : password.toCharArray()) {
            if (!seen.add(c)) {
                System.out.println("Caractere repetido encontrado: " + c);
                return false;
            }
        }
        System.out.println("Senha válida (sem caracteres repetidos): " + password);
        return true;
    }
}
