package com.epam.esm.security.jwt;

import com.epam.esm.security.jwt.authentication.JwtAuthenticationProvider;
import com.epam.esm.security.jwt.authentication.JwtAuthenticationProviderFactory;
import com.epam.esm.security.jwt.claims.JwtClaimsProvider;
import com.epam.esm.security.jwt.claims.JwtClaimsProviderFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * The {@code Jwt token support} works with jwt tokens.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenSupport {
    private final JwtClaimsProviderFactory claimsProviderFactory;
    private final JwtAuthenticationProviderFactory authenticationProviderFactory;

    /**
     * Generate new jwt token.
     *
     * @param authentication the authentication
     * @return the token
     */
    public String generateToken(Authentication authentication) {
        Date expiration = new Date(System.currentTimeMillis() + JwtConstant.EXPIRATION_TIME);
        JwtClaimsProvider jwtClaimsProvider = claimsProviderFactory.getJwtClaimsProvider(authentication);
        Claims claims = jwtClaimsProvider.provideClaims(authentication);
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
            log.info("Token validation not passed!", e);
        }
        return false;
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
        JwtAuthenticationProvider provider = authenticationProviderFactory.getJwtAuthenticationProvider(claims);
        return provider.provideAuthentication(claims);
    }
}
