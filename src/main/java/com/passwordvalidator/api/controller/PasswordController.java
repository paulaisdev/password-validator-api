package com.passwordvalidator.api.controller;

import com.passwordvalidator.api.dto.PasswordRequest;
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

    @PostMapping(value ="/validate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> validatePassword(@Valid @RequestBody PasswordRequest passwordRequest) {
        try {
            passwordMetrics.increment();

            String sanitizedPassword = InputSanitizer.sanitize(passwordRequest.getPassword());
            PasswordValidator.validate(sanitizedPassword);

            boolean isValid = cachedValidatorService.isValid(sanitizedPassword);
            return ResponseEntity.ok(isValid);
        } catch (PasswordValidationException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
