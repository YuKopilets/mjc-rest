package com.epam.esm.service.impl;

import com.epam.esm.exception.OAuth2UserNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OAuth2Authentication;
import com.epam.esm.service.OAuth2UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

/**
 * The type is implementation of OAuth user details service.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see OAuth2UserDetailsService
 */
@Component
@RequiredArgsConstructor
public class OAuth2UserDetailsServiceImpl implements OAuth2UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUserBySub(String oAuthType, String sub) throws OAuth2UserNotFoundException {
        OAuth2Authentication authentication = EnumUtils.getEnumIgnoreCase(OAuth2Authentication.class, oAuthType);
        return authentication.doAuthentication(userRepository, sub);
    }
}
