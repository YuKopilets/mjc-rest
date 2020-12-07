package com.epam.esm.jwt;

import com.epam.esm.service.OAuth2UserDetailsService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationProvider {
    private final UserDetailsService localUserDetailsService;
    private final OAuth2UserDetailsService oAuth2UserDetailsService;

    public Authentication provideLocalAuthentication(Claims claims) {
        String login = claims.getSubject();
        UserDetails userDetails = localUserDetailsService.loadUserByUsername(login);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public Authentication provideOAuthAuthentication(Claims claims) {
        String authenticationType = (String) claims.get(JwtConstant.AUTHENTICATION_TYPE_CLAIM);
        String sub = (String) claims.get(JwtConstant.OAUTH2_SUB_CLAIM);
        OAuth2User user = oAuth2UserDetailsService.loadUserBySub(authenticationType, sub);
        return new OAuth2AuthenticationToken(user, user.getAuthorities(), authenticationType);
    }
}
