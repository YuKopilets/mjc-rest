package com.epam.esm.security.util;

import com.epam.esm.security.service.local.LocalUserDetailsImpl;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * The {@code type Authentication utils} is utility class to operate with
 * security Authentication.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@UtilityClass
public class AuthenticationUtils {
    /**
     * Get authorized user id from current <i>Authentication</i>.
     *
     * @return the authorized user's id
     */
    public static Long getAuthorizedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId;
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User principal = authenticationToken.getPrincipal();
            userId = principal.getAttribute(UserAuthenticationAttributeConstant.USER_ID);
        } else {
            LocalUserDetailsImpl principal = (LocalUserDetailsImpl) authentication.getPrincipal();
            userId = principal.getId();
        }
        return userId;
    }
}
