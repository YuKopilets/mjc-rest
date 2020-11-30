package com.epam.esm.util;

import com.epam.esm.exception.ValidateJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;

import java.util.Date;

/**
 * The {@code Jwt utils} is utility class for working with jwt tokens.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@UtilityClass
public class JwtUtils {
    private static final Long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 30;

    private static String SECRET_KEY;

    /**
     * Generate token value based on login.
     *
     * @param login the login
     * @return the token
     */
    public static String generateToken(String login) {
        Date expiration = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * Validate token.
     *
     * @param token the token
     * @return is valid
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new ValidateJwtTokenException(e.getMessage(), e);
        }
    }

    /**
     * Get login from token.
     *
     * @param token the token
     * @return the login
     */
    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }
}
