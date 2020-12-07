package com.epam.esm.service.impl;

import com.epam.esm.service.OAuth2RegistrationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final DefaultOAuth2UserService delegateOAuth2UserService = new DefaultOAuth2UserService();
    private final OAuth2RegistrationHandler registrationHandler;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        String registrationId = request.getClientRegistration().getRegistrationId();
        OAuth2User oauth2User = delegateOAuth2UserService.loadUser(request);
        registrationHandler.doRegistration(oauth2User, registrationId);
        return oauth2User;
    }
}
