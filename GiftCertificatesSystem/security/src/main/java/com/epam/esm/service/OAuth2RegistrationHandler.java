package com.epam.esm.service;

import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2RegistrationHandler {
    private final UserRepository userRepository;

    public void doRegistration(OAuth2User user, String registrationId) {
        OAuth2Authentication registration = EnumUtils.getEnumIgnoreCase(OAuth2Authentication.class, registrationId);
        registration.doRegistration(userRepository, user);
    }
}
