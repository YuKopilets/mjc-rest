package com.epam.esm.jwt.authentication;

import com.epam.esm.jwt.JwtAuthenticationType;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface JwtAuthenticationProvider {
    Authentication provideAuthentication(Claims claims);

    JwtAuthenticationType getJwtAuthenticationType();
}
