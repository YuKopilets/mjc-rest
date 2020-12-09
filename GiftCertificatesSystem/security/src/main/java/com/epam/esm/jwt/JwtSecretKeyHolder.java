package com.epam.esm.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The {@code Jwt secret key holder} contains and provides secret key for
 * operations with jwt tokens.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
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
                    "Instance was already created!");
        }
    }

    public static JwtSecretKeyHolder getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JwtSecretKeyHolder();
        }
        return INSTANCE;
    }
}
