package com.example.passwordvalidator.rules;

import com.example.passwordvalidator.domain.rules.MinLengthRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinLengthRuleTest {

    private final MinLengthRule rule = new MinLengthRule(9);

    @Test
    void testPasswordBelowMinimumLength() {
        assertFalse(rule.validate("12345"));
    }

    @Test
    void testPasswordAtMinimumLength() {
        assertTrue(rule.validate("123456789"));
    }
}