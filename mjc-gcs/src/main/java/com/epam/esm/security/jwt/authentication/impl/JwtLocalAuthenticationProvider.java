package com.epam.esm.security.jwt.authentication.impl;

import com.epam.esm.security.jwt.JwtAuthenticationType;
import com.epam.esm.security.jwt.authentication.JwtAuthenticationProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * The implementation of Jwt authentication provider.
 * Provides local authentication.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see JwtAuthenticationProvider
 */
@Component
@RequiredArgsConstructor
public class JwtLocalAuthenticationProvider implements JwtAuthenticationProvider {
    private final UserDetailsService localUserDetailsService;

    @Override
    public Authentication provideAuthentication(Claims claims) {
        String login = claims.getSubject();
        UserDetails userDetails = localUserDetailsService.loadUserByUsername(login);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public JwtAuthenticationType getJwtAuthenticationType() {
        return JwtAuthenticationType.LOCAL;
    }
}
