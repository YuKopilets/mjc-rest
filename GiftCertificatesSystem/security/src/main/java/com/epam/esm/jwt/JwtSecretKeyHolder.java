package com.epam.esm.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtSecretKeyHolder {
    private static JwtSecretKeyHolder INSTANCE;

    @Getter
    @Value("${jwt.secret-key}")
    private byte[] secretKey;

    public JwtSecretKeyHolder() {
        if (INSTANCE == null) {
            INSTANCE = this;
        } else {
            throw new IllegalStateException("Attempt to create second object of singleton class. " +
                    "Instance is already created!");
        }
    }

    public static JwtSecretKeyHolder getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JwtSecretKeyHolder();
        }
        return INSTANCE;
    }
}
