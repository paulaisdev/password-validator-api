package com.passwordvalidator.api.controller;

import com.passwordvalidator.api.metrics.PasswordMetrics;
import com.passwordvalidator.api.service.CachedPasswordValidatorService;
import com.passwordvalidator.api.util.InputSanitizer;
import com.passwordvalidator.api.validator.PasswordValidationException;
import com.passwordvalidator.api.validator.PasswordValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private final CachedPasswordValidatorService cachedValidatorService;
    private final PasswordMetrics passwordMetrics;

    public PasswordController(CachedPasswordValidatorService cachedValidatorService, PasswordMetrics passwordMetrics) {
        this.cachedValidatorService = cachedValidatorService;
        this.passwordMetrics = passwordMetrics;
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validatePassword(@RequestBody(required = false) String password) {
        try {
            passwordMetrics.increment();

            String sanitizedPassword = InputSanitizer.sanitize(password);
            System.out.println("CONTROLLER SENHA SANITIZADA" + sanitizedPassword);
            PasswordValidator.validate(sanitizedPassword);

            boolean isValid = cachedValidatorService.isValid(sanitizedPassword);
            return ResponseEntity.ok(isValid);
        } catch (PasswordValidationException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
