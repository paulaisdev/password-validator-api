package com.passwordvalidator.api.metrics;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class PasswordMetrics {

    private final Counter passwordValidationCounter;

    public PasswordMetrics(MeterRegistry registry) {
        this.passwordValidationCounter = registry.counter("password.validations");
    }

    public void increment() {
        passwordValidationCounter.increment();
    }
}

