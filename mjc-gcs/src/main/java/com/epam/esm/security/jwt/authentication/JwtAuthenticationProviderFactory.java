package com.epam.esm.security.jwt.authentication;

import com.epam.esm.service.exception.JwtAuthenticationProviderNotFoundException;
import com.epam.esm.security.jwt.JwtAuthenticationType;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Jwt authentication provider factory.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProviderFactory {
    private final List<JwtAuthenticationProvider> providers;

    /**
     * Get jwt authentication provider.
     *
     * @param claims the claims
     * @return the jwt authentication provider
     * @throws JwtAuthenticationProviderNotFoundException the jwt
     * authentication provider not found exception
     */
    public JwtAuthenticationProvider getJwtAuthenticationProvider(Claims claims)
            throws JwtAuthenticationProviderNotFoundException {
        JwtAuthenticationType jwtAuthenticationType = JwtAuthenticationType.defineAuthenticationType(claims);
        return providers.stream()
                .filter(provider -> provider.getJwtAuthenticationType() == jwtAuthenticationType)
                .findFirst()
                .orElseThrow(() -> new JwtAuthenticationProviderNotFoundException(
                        "Provider not found by jwt authentication type: " + jwtAuthenticationType.name())
                );
    }
}
