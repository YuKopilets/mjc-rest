package com.epam.esm.security.jwt.authentication;

import com.epam.esm.security.jwt.JwtAuthenticationType;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

/**
 * The interface Jwt authentication provider.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
public interface JwtAuthenticationProvider {
    /**
     * Provide authentication based on claims from jwt token.
     *
     * @param claims the claims
     * @return the authentication
     */
    Authentication provideAuthentication(Claims claims);

    /**
     * Get jwt authentication type.
     *
     * @return the jwt authentication type
     */
    JwtAuthenticationType getJwtAuthenticationType();
}
