package com.epam.esm.security.service.oauth2;

import com.epam.esm.service.exception.OAuth2UserNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * Core interface which loads user-specific data.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
public interface OAuth2UserDetailsService {
    /**
     * Locates the oauth user based on the sub.
     * In this case, the <code>OAuth2User</code> object that comes back
     * from the database.
     *
     * @param oauthType the o auth type
     * @param sub       the oauth sub
     * @return the user
     * @throws OAuth2UserNotFoundException the user by sub not found exception
     */
    OAuth2User loadUserBySub(String oauthType, String sub) throws OAuth2UserNotFoundException;
}
