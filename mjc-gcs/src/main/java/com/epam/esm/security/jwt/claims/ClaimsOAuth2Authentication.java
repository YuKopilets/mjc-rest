package com.epam.esm.security.jwt.claims;

import com.epam.esm.security.jwt.JwtConstant;
import com.epam.esm.security.util.UserAuthenticationAttributeConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The {@code enum ClaimsOAuth2Authentication} creates jwt claims depends on
 * oauth authentication type.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@RequiredArgsConstructor
public enum ClaimsOAuth2Authentication {
    GOOGLE(UserAuthenticationAttributeConstant.GOOGLE_USER_SUB, attribute -> (String) attribute),
    GITHUB(UserAuthenticationAttributeConstant.GITHUB_USER_SUB, attribute -> ((Integer) attribute).toString());

    private final String subAttributeName;
    private final Function<Object, String> castSubAttributeFunction;

    /**
     * Create claims.
     *
     * @param authentication the authentication
     * @return the claims
     */
    public Claims createClaims(OAuth2AuthenticationToken authentication) {
        Map<String, Object> claims = new HashMap<>();
        OAuth2User user = authentication.getPrincipal();
        Object subAttribute = user.getAttribute(this.subAttributeName);
        String sub = castSubAttribute(subAttribute);
        claims.put(JwtConstant.AUTHENTICATION_TYPE_CLAIM, name().toLowerCase());
        claims.put(JwtConstant.OAUTH2_SUB_CLAIM, sub);
        return Jwts.claims(claims);
    }

    private String castSubAttribute(Object attribute) {
        return this.castSubAttributeFunction.apply(attribute);
    }
}
