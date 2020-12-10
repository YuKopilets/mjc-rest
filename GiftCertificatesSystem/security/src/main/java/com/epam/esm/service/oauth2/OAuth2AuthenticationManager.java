package com.epam.esm.service.oauth2;

import com.epam.esm.exception.OAuth2UserNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2AuthenticationManager {
    void doRegistration(OAuth2User oauth2User);

    OAuth2User doAuthentication(String sub) throws OAuth2UserNotFoundException;

    String getAuthenticationName();
}
