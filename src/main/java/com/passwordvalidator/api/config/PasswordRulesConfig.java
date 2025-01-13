package com.passwordvalidator.api.config;

import com.passwordvalidator.api.domain.PasswordRule;
import com.example.passwordvalidator.domain.rules.*;
import com.passwordvalidator.api.domain.rules.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
public class PasswordRulesConfig {

    @Bean
    public List<PasswordRule> passwordRules() {
        return List.of(
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
        );
    }

}