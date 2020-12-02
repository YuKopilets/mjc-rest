package com.epam.esm.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JwtConstantHolder {
    private static final Long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 30;
    private static byte[] SECRET_KEY;

    @Value("${jwt.secret-key}")
    private byte[] secretKey;

    @PostConstruct
    private void init() {
        SECRET_KEY = this.secretKey;
    }

    public static Long getExpirationTime() {
        return EXPIRATION_TIME;
    }

    public static byte[] getSecretKey() {
        return SECRET_KEY;
    }
}
