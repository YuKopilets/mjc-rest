package com.epam.esm.service.oauth2.userinfo;

import com.epam.esm.exception.OAuth2UserNotFoundException;
import com.epam.esm.service.oauth2.OAuth2UserDetailsService;
import com.epam.esm.service.oauth2.handler.OAuth2AuthenticationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * The type is implementation of OAuth user details service.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see OAuth2UserDetailsService
 */
@Service
@RequiredArgsConstructor
public class OAuth2UserDetailsServiceImpl implements OAuth2UserDetailsService {
    private final OAuth2AuthenticationHandler authenticationHandler;

    @Override
    public OAuth2User loadUserBySub(String oauthType, String sub) throws OAuth2UserNotFoundException {
        return authenticationHandler.doAuthentication(oauthType, sub);
    }
}
