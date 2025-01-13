package com.example.passwordvalidator.controller;

import com.example.passwordvalidator.service.PasswordValidatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private final PasswordValidatorService passwordValidatorService;

    public PasswordController(PasswordValidatorService passwordValidatorService) {
        this.passwordValidatorService = passwordValidatorService;
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validatePassword(@RequestBody String password) {
        if (password == null || password.length() > 100) {
            return ResponseEntity.badRequest().build();
        }
        boolean isValid = passwordValidatorService.isValid(password);
        return ResponseEntity.ok(isValid);
    }
}