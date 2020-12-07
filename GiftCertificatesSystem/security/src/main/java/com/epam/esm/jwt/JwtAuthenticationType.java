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

    /**
     * Create jwt claims.
     *
     * @param jwtClaimsProvider the jwt claims provider
     * @param authentication    the authentication
     * @return the claims
     */
    public static Claims createJwtClaims(JwtClaimsProvider jwtClaimsProvider, Authentication authentication) {
        JwtAuthenticationType type = defineAuthenticationType(authentication);
        if (type == LOCAL) {
            return jwtClaimsProvider.provideLocalUserClaims(authentication);
        } else {
            return jwtClaimsProvider.provideOAuthUserClaims((OAuth2AuthenticationToken) authentication);
        }
    }

    /**
     * Generate authentication token.
     *
     * @param authenticationProvider the authentication provider
     * @param claims                 the claims
     * @return the authentication token
     */
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
