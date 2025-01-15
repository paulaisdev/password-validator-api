package com.passwordvalidator.api.domain.rules;

import com.passwordvalidator.api.domain.rules.ValidCharacterRule;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidCharacterRuleTest {
    @Test
    void testValidCharacterRule() {
        Set<Character> allowed = Set.of('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        ValidCharacterRule rule = new ValidCharacterRule(allowed);

        assertTrue(rule.validate("AbTp9!fok"));
        assertFalse(rule.validate("AbTp9!fok🙂"));
    }

}
