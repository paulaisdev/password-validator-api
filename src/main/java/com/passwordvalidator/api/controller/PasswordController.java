package com.passwordvalidator.api.controller;

import com.passwordvalidator.api.metrics.PasswordMetrics;
import com.passwordvalidator.api.service.CachedPasswordValidatorService;
import com.passwordvalidator.api.util.InputSanitizer;
import com.passwordvalidator.api.exception.PasswordValidationException;
import com.passwordvalidator.api.service.validator.PasswordValidator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/password", consumes = MediaType.APPLICATION_JSON_VALUE)
public class PasswordController {

    private final CachedPasswordValidatorService cachedValidatorService;
    private final PasswordMetrics passwordMetrics;

    public PasswordController(CachedPasswordValidatorService cachedValidatorService, PasswordMetrics passwordMetrics) {
        this.cachedValidatorService = cachedValidatorService;
        this.passwordMetrics = passwordMetrics;
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validatePassword(@Valid @RequestBody String password) {
        try {
            passwordMetrics.increment();

            String sanitizedPassword = InputSanitizer.sanitize(password);
            PasswordValidator.validate(sanitizedPassword);

            boolean isValid = cachedValidatorService.isValid(sanitizedPassword);
            return ResponseEntity.ok(isValid);
        } catch (PasswordValidationException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
