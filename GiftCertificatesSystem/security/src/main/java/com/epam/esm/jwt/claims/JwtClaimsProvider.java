package com.epam.esm.jwt.claims;

import com.epam.esm.jwt.JwtAuthenticationType;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface JwtClaimsProvider {
    Claims provideClaims(Authentication authentication);

    JwtAuthenticationType getJwtAuthenticationType();
}
