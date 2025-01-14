package com.passwordvalidator.api.controller;

import com.passwordvalidator.api.metrics.PasswordMetrics;
import com.passwordvalidator.api.service.CachedPasswordValidatorService;
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
            passwordMetrics.increment(); // Incrementa métrica personalizada
            PasswordValidator.validate(password); // Validação centralizada
            boolean isValid = cachedValidatorService.isValid(password); // Validação cacheada
            return ResponseEntity.ok(isValid);
        } catch (PasswordValidationException e) {
            return ResponseEntity.badRequest().body(false); // Retorna erro 400
        }
    }
}
