package com.epam.esm.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * The {@code Jwt secret key holder} contains and provides secret key for
 * operations with jwt tokens.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
public class JwtSecretKeyHolder {
    @Getter
    private final byte[] secretKey;

    @Component
    private static class JwtSecretKeyInstanceHolder {
        private static JwtSecretKeyHolder INSTANCE;

        @Value("${jwt.secret-key}")
        private byte[] secretKey;

        @PostConstruct
        private void initInstance() {
            INSTANCE = new JwtSecretKeyHolder(secretKey);
        }
    }

    private JwtSecretKeyHolder(byte[] secretKey) {
        this.secretKey = secretKey;
    }

    public static JwtSecretKeyHolder getInstance() {
        return JwtSecretKeyInstanceHolder.INSTANCE;
    }
}
