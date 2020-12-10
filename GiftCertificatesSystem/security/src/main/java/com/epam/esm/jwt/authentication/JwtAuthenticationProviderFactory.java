package com.epam.esm.jwt.authentication;

import com.epam.esm.exception.JwtAuthenticationProviderNotFoundException;
import com.epam.esm.jwt.JwtAuthenticationType;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProviderFactory {
    private final List<JwtAuthenticationProvider> providers;

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
