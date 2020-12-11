package com.epam.esm.jwt.claims;

import com.epam.esm.jwt.JwtAuthenticationType;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

/**
 * The interface Jwt claims provider.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
public interface JwtClaimsProvider {
    /**
     * Provide claims based on current authentication.
     *
     * @param authentication the authentication
     * @return the claims
     */
    Claims provideClaims(Authentication authentication);

    /**
     * Get jwt authentication type.
     *
     * @return the jwt authentication type
     */
    JwtAuthenticationType getJwtAuthenticationType();
}
