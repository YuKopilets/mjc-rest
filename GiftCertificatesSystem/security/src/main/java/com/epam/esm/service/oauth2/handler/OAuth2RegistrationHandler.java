package com.epam.esm.service.oauth2.handler;

import com.epam.esm.service.oauth2.manager.AbstractOAuth2AuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Registration handler based on registration oauth user logic.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class OAuth2RegistrationHandler {
    private final List<AbstractOAuth2AuthenticationManager> managers;

    /**
     * Do process of registration oauth user.
     *
     * @param user           the oauth user
     * @param registrationId the oauth registration id
     */
    public void doRegistration(OAuth2User user, String registrationId) {
        managers.stream()
                .filter(manager -> manager.getAuthenticationName().equalsIgnoreCase(registrationId))
                .findFirst()
                .ifPresent(manager -> manager.doRegistration(user));
    }
}
