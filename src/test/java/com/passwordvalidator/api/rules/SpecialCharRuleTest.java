package com.passwordvalidator.api.rules;

import com.passwordvalidator.api.domain.rules.SpecialCharRule;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpecialCharRuleTest {

    private final SpecialCharRule rule = new SpecialCharRule(Set.of('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+'));

    @Test
    void testPasswordWithSpecialCharacter() {
        assertTrue(rule.validate("AbTp9!fok"));
    }

    @Test
    void testPasswordWithoutSpecialCharacter() {
        assertFalse(rule.validate("AbTp9fok"));
    }
}