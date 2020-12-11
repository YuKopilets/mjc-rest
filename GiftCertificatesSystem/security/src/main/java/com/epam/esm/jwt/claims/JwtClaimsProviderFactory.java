package com.epam.esm.jwt.claims;

import com.epam.esm.exception.JwtClaimsProviderNotFoundException;
import com.epam.esm.jwt.JwtAuthenticationType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Jwt claims provider factory.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class JwtClaimsProviderFactory {
    private final List<JwtClaimsProvider> providers;

    /**
     * Get jwt claims provider.
     *
     * @param authentication the authentication
     * @return the jwt claims provider
     * @throws JwtClaimsProviderNotFoundException the jwt claims provider
     * not found exception
     */
    public JwtClaimsProvider getJwtClaimsProvider(Authentication authentication)
            throws JwtClaimsProviderNotFoundException {
        JwtAuthenticationType jwtAuthenticationType = JwtAuthenticationType.defineAuthenticationType(authentication);
        return providers.stream()
                .filter(provider -> provider.getJwtAuthenticationType() == jwtAuthenticationType)
                .findFirst()
                .orElseThrow(() -> new JwtClaimsProviderNotFoundException(
                        "Provider not found by jwt authentication type: " + jwtAuthenticationType.name())
                );
    }
}
