package com.passwordvalidator.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        String filePath = "src/main/resources/openapi/openapi.yaml";
        OpenAPI openAPI = new OpenAPIV3Parser().read(filePath);

        if (openAPI.getPaths() == null) {
            openAPI.setPaths(new Paths());
        }
        return openAPI;
    }
}
