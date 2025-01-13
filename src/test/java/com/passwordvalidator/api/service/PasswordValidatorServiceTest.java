package com.passwordvalidator.api.service;

import com.passwordvalidator.api.domain.PasswordRule;
import com.passwordvalidator.api.domain.rules.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PasswordValidatorServiceTest {
    private final PasswordRule mockRule = mock(PasswordRule.class);

    private final PasswordValidatorService passwordValidatorService = new PasswordValidatorService(
            List.of(
                    new MinLengthRule(9),
                    new UpperCaseRule(),
                    new SpecialCharRule(Set.of('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+')),
                    new UniqueCharacterRule(),
                    new ValidCharacterRule(Set.of('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+',
                            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'))
            )
    );

    @Test
    void testServiceWithMockedRule() {
        when(mockRule.validate("Test123")).thenReturn(true);
        assertTrue(mockRule.validate("Test123"));
        verify(mockRule, times(1)).validate("Test123");
    }

    @ParameterizedTest
    @CsvSource({
            "'AbTp9!fok', true",
            "'12345', false",
            "'AbTp!fo', false",
            "'1234567890', false"
    })

    @Test
    void testEmptyPassword() {
        assertFalse(passwordValidatorService.isValid(""));
    }

    @Test
    void testPasswordWithOnlySpaces() {
        assertFalse(passwordValidatorService.isValid("     "));
    }

    @Test
    void testPasswordWithRepeatedCharacters() {
        assertFalse(passwordValidatorService.isValid("AbTp9!fokA"));
    }

    @Test
    void testPasswordWithoutSpecialCharacters() {
        assertFalse(passwordValidatorService.isValid("AbTp9fok123"));
    }

    @Test
    void testPasswordWithoutUppercase() {
        assertFalse(passwordValidatorService.isValid("abtp9!fok"));
    }

    @Test
    void testPasswordWithoutDigits() {
        assertFalse(passwordValidatorService.isValid("AbTp!fok"));
    }

    @Test
    void testValidLongPassword() {
        assertTrue(passwordValidatorService.isValid("AbTp9!fok12345"));
    }

    @Test
    void testPasswordWithInvalidCharacters() {
        assertFalse(passwordValidatorService.isValid("AbTp9!fok🙂"));
    }

    void testPasswordValidationParametrized(String password, boolean expected) {
        assertEquals(expected, passwordValidatorService.isValid(password));
    }
}