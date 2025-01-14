package com.passwordvalidator.api.controller;

import com.passwordvalidator.api.service.CachedPasswordValidatorService;
import com.passwordvalidator.api.service.PasswordValidatorService;
import com.passwordvalidator.api.validator.PasswordValidationException;
import com.passwordvalidator.api.validator.PasswordValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private static final int MAX_PASSWORD_LENGTH = 24;
    private final CachedPasswordValidatorService cachedValidatorService;

    public PasswordController(CachedPasswordValidatorService cachedValidatorService) {
        this.cachedValidatorService = cachedValidatorService;
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validatePassword(@RequestBody String password) {
        try {
            PasswordValidator.validate(password);
            boolean isValid = cachedValidatorService.isValid(password);
            return ResponseEntity.ok(isValid);
        } catch (PasswordValidationException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}