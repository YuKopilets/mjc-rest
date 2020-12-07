package com.epam.esm.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtClaimsProvider {
    private static final String LOCAL_AUTHENTICATION = "local";

    public Claims provideLocalUserClaims(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtConstant.AUTHENTICATION_TYPE_CLAIM, LOCAL_AUTHENTICATION);
        return Jwts.claims(claims).setSubject(authentication.getName());
    }

    public Claims provideOAuthUserClaims(OAuth2AuthenticationToken authentication) {
        String registrationId = authentication.getAuthorizedClientRegistrationId();
        ClaimsOAuth2Authentication type = EnumUtils.getEnumIgnoreCase(ClaimsOAuth2Authentication.class, registrationId);
        return type.createClaims(authentication);
    }
}
