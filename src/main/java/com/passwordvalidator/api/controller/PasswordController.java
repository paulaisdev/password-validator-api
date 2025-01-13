package com.passwordvalidator.api.controller;

import com.passwordvalidator.api.service.CachedPasswordValidatorService;
import com.passwordvalidator.api.service.PasswordValidatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private final CachedPasswordValidatorService cachedValidatorService;

    public PasswordController(CachedPasswordValidatorService cachedValidatorService) {
        this.cachedValidatorService = cachedValidatorService;
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validatePassword(@RequestBody String password) {
        if (password == null || password.isEmpty() || password.length() > 100) {
            return ResponseEntity.badRequest().build();
        }
        boolean isValid = cachedValidatorService.isValid(password);
        return ResponseEntity.ok(isValid);
    }
}