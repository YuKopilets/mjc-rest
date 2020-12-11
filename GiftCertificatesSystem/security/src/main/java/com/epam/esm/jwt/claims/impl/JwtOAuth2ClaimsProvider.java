package com.epam.esm.jwt.claims.impl;

import com.epam.esm.jwt.JwtAuthenticationType;
import com.epam.esm.jwt.claims.ClaimsOAuth2Authentication;
import com.epam.esm.jwt.claims.JwtClaimsProvider;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * The implementation of Jwt claims provider.
 * Provides claims based on oauth authentication.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see JwtClaimsProvider
 */
@Component
public class JwtOAuth2ClaimsProvider implements JwtClaimsProvider {
    @Override
    public Claims provideClaims(Authentication authentication) {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        String registrationId = authenticationToken.getAuthorizedClientRegistrationId();
        ClaimsOAuth2Authentication type = EnumUtils.getEnumIgnoreCase(ClaimsOAuth2Authentication.class, registrationId);
        return type.createClaims(authenticationToken);
    }

    @Override
    public JwtAuthenticationType getJwtAuthenticationType() {
        return JwtAuthenticationType.OAUTH2;
    }
}
