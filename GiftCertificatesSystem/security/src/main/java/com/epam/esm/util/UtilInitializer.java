package com.epam.esm.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UtilInitializer {
    @Value("${jwt.secret-key}")
    private byte[] secretKey;

    @PostConstruct
    public void initialize() {
        JwtUtils.setSecretKey(secretKey);
    }
}
