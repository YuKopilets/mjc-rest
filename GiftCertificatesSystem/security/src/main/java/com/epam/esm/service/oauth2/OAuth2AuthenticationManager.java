package com.epam.esm.service.oauth2;

import com.epam.esm.exception.OAuth2UserNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * The interface authentication manager.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
public interface OAuth2AuthenticationManager {
    /**
     * Do registration.
     *
     * @param oauth2User the oauth user
     */
    void doRegistration(OAuth2User oauth2User);

    /**
     * Do authentication oauth user.
     *
     * @param sub the sub
     * @return the oauth user
     * @throws OAuth2UserNotFoundException the oauth user not found exception
     */
    OAuth2User doAuthentication(String sub) throws OAuth2UserNotFoundException;

    /**
     * Get authentication name.
     *
     * @return the authentication name
     */
    String getAuthenticationName();
}
