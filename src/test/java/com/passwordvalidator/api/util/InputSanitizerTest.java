package com.passwordvalidator.api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InputSanitizerTest {

    @Test
    void testSanitize_NullInput() {
        String sanitized = InputSanitizer.sanitize(null);
        assertNull(sanitized);
    }

    @Test
    void testSanitize_ValidInput() {
        String input = "AbTp9!fok";
        String sanitized = InputSanitizer.sanitize(input);
        assertEquals("AbTp9!fok", sanitized);
    }
}
