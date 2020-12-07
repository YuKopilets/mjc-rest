package com.epam.esm.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * The {@code Jwt utils} is utility class for working with jwt tokens.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class JwtTokenSupport {
    private final JwtClaimsProvider jwtClaimsProvider;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Generate new jwt token.
     *
     * @param authentication the authentication
     * @return the token
     */
    public String generateToken(Authentication authentication) {
        Date expiration = new Date(System.currentTimeMillis() + JwtConstant.EXPIRATION_TIME);
        Claims claims = JwtAuthenticationType.createJwtClaims(jwtClaimsProvider, authentication);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, JwtSecretKeyHolder.getInstance().getSecretKey())
                .compact();
    }

    /**
     * Validate token.
     *
     * @param token the token
     * @return is valid
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(JwtSecretKeyHolder.getInstance().getSecretKey())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Parse token to authentication.
     *
     * @param token the token
     * @return the authentication
     */
    public Authentication parseTokenToAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JwtSecretKeyHolder.getInstance().getSecretKey())
                .parseClaimsJws(token)
                .getBody();
        return JwtAuthenticationType.generateAuthentication(authenticationProvider, claims);
    }
}
