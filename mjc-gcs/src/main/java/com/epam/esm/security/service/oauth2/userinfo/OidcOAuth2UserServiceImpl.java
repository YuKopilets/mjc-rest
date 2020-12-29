package com.epam.esm.security.service.oauth2.userinfo;

import com.epam.esm.security.service.oauth2.handler.OAuth2RegistrationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

/**
 * The type is implementation of Oidc user service.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see OidcUserService
 */
@Service
@RequiredArgsConstructor
public class OidcOAuth2UserServiceImpl extends OidcUserService {
    private final OAuth2RegistrationHandler registrationHandler;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OidcUser oidcUser = super.loadUser(userRequest);
        registrationHandler.doRegistration(oidcUser, registrationId);
        return oidcUser;
    }
}
