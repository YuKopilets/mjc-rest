package com.epam.esm.service;

import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

/**
 * The type Registration handler based on registration oauth user logic.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class OAuth2RegistrationHandler {
    private final UserRepository userRepository;

    /**
     * Do process of registration oauth user.
     *
     * @param user           the oauth user
     * @param registrationId the oauth registration id
     */
    public void doRegistration(OAuth2User user, String registrationId) {
        OAuth2Authentication registration = EnumUtils.getEnumIgnoreCase(OAuth2Authentication.class, registrationId);
        registration.doRegistration(userRepository, user);
    }
}
