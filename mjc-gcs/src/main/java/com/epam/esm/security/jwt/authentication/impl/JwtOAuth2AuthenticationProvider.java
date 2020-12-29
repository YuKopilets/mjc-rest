package com.epam.esm.security.jwt.authentication.impl;

import com.epam.esm.security.jwt.JwtAuthenticationType;
import com.epam.esm.security.jwt.JwtConstant;
import com.epam.esm.security.jwt.authentication.JwtAuthenticationProvider;
import com.epam.esm.security.service.oauth2.OAuth2UserDetailsService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

/**
 * The implementation of Jwt authentication provider.
 * Provides oauth authentication.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see JwtAuthenticationProvider
 */
@Component
@RequiredArgsConstructor
public class JwtOAuth2AuthenticationProvider implements JwtAuthenticationProvider {
    private final OAuth2UserDetailsService oAuth2UserDetailsService;

    @Override
    public Authentication provideAuthentication(Claims claims) {
        String authenticationType = (String) claims.get(JwtConstant.AUTHENTICATION_TYPE_CLAIM);
        String sub = (String) claims.get(JwtConstant.OAUTH2_SUB_CLAIM);
        OAuth2User user = oAuth2UserDetailsService.loadUserBySub(authenticationType, sub);
        return new OAuth2AuthenticationToken(user, user.getAuthorities(), authenticationType);
    }

    @Override
    public JwtAuthenticationType getJwtAuthenticationType() {
        return JwtAuthenticationType.OAUTH2;
    }
}
