package com.passwordvalidator.api.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class CachedPasswordValidatorService {

    private final PasswordValidatorService validatorService;
    private final Cache<String, Boolean> cache;

    public CachedPasswordValidatorService(PasswordValidatorService validatorService, Cache<String, Boolean> cache) {
        this.validatorService = validatorService;
        this.cache = cache;
    }

    public boolean isValid(String password) {
        return cache.get(password, validatorService::isValid);
    }
}
