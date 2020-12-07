package com.epam.esm.service;

import com.epam.esm.exception.OAuth2UserNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2UserDetailsService {
    OAuth2User loadUserBySub(String oAuthType, String sub) throws OAuth2UserNotFoundException;
}
