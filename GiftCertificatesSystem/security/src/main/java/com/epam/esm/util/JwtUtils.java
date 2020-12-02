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
    /**
     * Generate token value based on login.
     *
     * @param login the login
     * @return the token
     */
    public static String generateToken(String login) {
        Date expiration = new Date(System.currentTimeMillis() + JwtConstantHolder.getExpirationTime());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, JwtConstantHolder.getSecretKey())
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
                    .setSigningKey(JwtConstantHolder.getSecretKey())
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
                .setSigningKey(JwtConstantHolder.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
