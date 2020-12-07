package com.epam.esm.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

@RequiredArgsConstructor
public enum JwtAuthenticationType {
    LOCAL,
    OAUTH2;

    public static Claims createJwtClaims(JwtClaimsProvider jwtClaimsProvider, Authentication authentication) {
        JwtAuthenticationType type = defineAuthenticationType(authentication);
        if (type == LOCAL) {
            return jwtClaimsProvider.provideLocalUserClaims(authentication);
        } else {
            return jwtClaimsProvider.provideOAuthUserClaims((OAuth2AuthenticationToken) authentication);
        }
    }

    public static Authentication generateAuthentication(AuthenticationProvider authenticationProvider, Claims claims) {
        JwtAuthenticationType type = defineAuthenticationType(claims);
        if (type == LOCAL) {
            return authenticationProvider.provideLocalAuthentication(claims);
        } else {
            return authenticationProvider.provideOAuthAuthentication(claims);
        }
    }

    private static JwtAuthenticationType defineAuthenticationType(Authentication authentication) {
        return authentication instanceof OAuth2AuthenticationToken ? OAUTH2 : LOCAL;
    }

    private static JwtAuthenticationType defineAuthenticationType(Claims claims) {
        return LOCAL.name().equalsIgnoreCase((String) claims.get(JwtConstant.AUTHENTICATION_TYPE_CLAIM)) ? LOCAL
                : OAUTH2;
    }
}
