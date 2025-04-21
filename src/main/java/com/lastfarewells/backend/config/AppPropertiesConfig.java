package com.lastfarewells.backend.config;

import com.lastfarewells.backend.utils.JWTUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppPropertiesConfig {

    @Value("${jwt.secret-key:default-secret-key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        if ("default-secret-key".equals(secretKey)) {
            throw new IllegalStateException("JWT secret key is not configured!");
        }
        JWTUtils.setSecretKey(secretKey);
    }
}
