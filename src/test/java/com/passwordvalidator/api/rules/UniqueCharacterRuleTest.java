package com.passwordvalidator.api.rules;

import com.passwordvalidator.api.domain.rules.UniqueCharacterRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UniqueCharacterRuleTest {
    @Test
    void testUniqueCharacterRule() {
        UniqueCharacterRule rule = new UniqueCharacterRule();

        assertTrue(rule.validate("AbTp9!fok")); // Sem caracteres repetidos
        assertFalse(rule.validate("AbTp9!foA")); // Com caracteres repetidos
    }

}
