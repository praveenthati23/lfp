package com.lastfarewells.backend.config;

import com.lastfarewells.backend.utils.JWTUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppPropertiesConfig {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        JWTUtils.setSecretKey(secretKey);
    }

}
