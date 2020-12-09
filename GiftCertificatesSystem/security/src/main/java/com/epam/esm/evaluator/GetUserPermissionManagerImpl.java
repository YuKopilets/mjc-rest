package com.epam.esm.evaluator;

import com.epam.esm.service.impl.LocalUserDetailsImpl;
import com.epam.esm.util.UserAuthenticationAttributeConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class GetUserPermissionManagerImpl implements PermissionManager {
    private final UserDetailsService userDetailsService;

    @Override
    public boolean test(Authentication authentication, Object targetObject) {
        Long id = (Long) targetObject;
        Long authorizedId;
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User principal = authenticationToken.getPrincipal();
            authorizedId = principal.getAttribute(UserAuthenticationAttributeConstant.USER_ID);
        } else {
            String login = authentication.getName();
            LocalUserDetailsImpl userDetails = (LocalUserDetailsImpl) userDetailsService.loadUserByUsername(login);
            authorizedId = userDetails.getId();
        }
        return Objects.equals(authorizedId, id);
    }

    @Override
    public String getPermissionName() {
        return Permission.GET_USER.getPermissionName();
    }
}
