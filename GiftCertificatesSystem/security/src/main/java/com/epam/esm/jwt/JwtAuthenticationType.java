package com.epam.esm.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

/**
 * The enum Jwt authentication contains authentication types.
 * The {@code JwtAuthenticationType} does operations based on
 * authentication type.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@RequiredArgsConstructor
public enum JwtAuthenticationType {
    LOCAL,
    OAUTH2;

    public static JwtAuthenticationType defineAuthenticationType(Authentication authentication) {
        return authentication instanceof OAuth2AuthenticationToken ? OAUTH2 : LOCAL;
    }

    public static JwtAuthenticationType defineAuthenticationType(Claims claims) {
        return LOCAL.name().equalsIgnoreCase((String) claims.get(JwtConstant.AUTHENTICATION_TYPE_CLAIM)) ? LOCAL
                : OAUTH2;
    }
}
