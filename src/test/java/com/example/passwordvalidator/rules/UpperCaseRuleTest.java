package com.example.passwordvalidator.rules;

import com.example.passwordvalidator.domain.rules.UpperCaseRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpperCaseRuleTest {

    private final UpperCaseRule rule = new UpperCaseRule();

    @Test
    void testPasswordWithUppercase() {
        assertTrue(rule.validate("AbTp9!fok"));
    }

    @Test
    void testPasswordWithoutUppercase() {
        assertFalse(rule.validate("abtp9!fok"));
    }
}