package com.epam.esm.jwt.claims;

import com.epam.esm.exception.JwtClaimsProviderNotFoundException;
import com.epam.esm.jwt.JwtAuthenticationType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtClaimsProviderFactory {
    private final List<JwtClaimsProvider> providers;

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
