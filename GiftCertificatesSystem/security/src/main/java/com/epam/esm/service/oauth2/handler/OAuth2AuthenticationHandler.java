package com.epam.esm.service.oauth2.handler;

import com.epam.esm.exception.AuthenticationManagerNotFoundException;
import com.epam.esm.service.oauth2.manager.AbstractOAuth2AuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Authentication handler based on authentication oauth user logic.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationHandler {
    private final List<AbstractOAuth2AuthenticationManager> managers;

    /**
     * Do process of authentication oauth user.
     *
     * @param oauthType the oauth type
     * @param sub       the oauth sub
     * @return the oauth user
     * @throws AuthenticationManagerNotFoundException the authentication
     * manager not found exception
     */
    public OAuth2User doAuthentication(String oauthType, String sub) throws AuthenticationManagerNotFoundException {
        return managers.stream()
                .filter(manager -> manager.getAuthenticationName().equalsIgnoreCase(oauthType))
                .findFirst()
                .orElseThrow(() -> new AuthenticationManagerNotFoundException(
                        "OAuth authentication manager not found by oauth type: " + oauthType)
                )
                .doAuthentication(sub);
    }
}
