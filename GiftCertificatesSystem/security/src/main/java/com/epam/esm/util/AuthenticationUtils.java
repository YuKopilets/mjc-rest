package com.epam.esm.util;

import com.epam.esm.service.impl.LocalUserDetailsImpl;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

@UtilityClass
public class AuthenticationUtils {
    public static Long getAuthorizedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId;
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User principal = authenticationToken.getPrincipal();
            userId = principal.getAttribute("id");
        } else {
            LocalUserDetailsImpl principal = (LocalUserDetailsImpl) authentication.getPrincipal();
            userId = principal.getId();
        }
        return userId;
    }
}
