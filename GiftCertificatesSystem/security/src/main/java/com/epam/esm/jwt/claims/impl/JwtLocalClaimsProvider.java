package com.epam.esm.jwt.claims.impl;

import com.epam.esm.jwt.JwtAuthenticationType;
import com.epam.esm.jwt.JwtConstant;
import com.epam.esm.jwt.claims.JwtClaimsProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtLocalClaimsProvider implements JwtClaimsProvider {
    private static final String LOCAL_AUTHENTICATION = "local";

    @Override
    public Claims provideClaims(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtConstant.AUTHENTICATION_TYPE_CLAIM, LOCAL_AUTHENTICATION);
        return Jwts.claims(claims).setSubject(authentication.getName());
    }

    @Override
    public JwtAuthenticationType getJwtAuthenticationType() {
        return JwtAuthenticationType.LOCAL;
    }
}
