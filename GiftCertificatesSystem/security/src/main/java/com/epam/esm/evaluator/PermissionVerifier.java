package com.epam.esm.evaluator;

import com.epam.esm.service.UserAuthenticationAttributeConstant;
import com.epam.esm.service.impl.LocalUserDetailsImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * The {@code Permission verifier} verifies custom permission.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Component
public class PermissionVerifier {
    private final Map<Permission, BiPredicate<Authentication, Object>> permissions;
    private final UserDetailsService userDetailsService;

    public PermissionVerifier(@Qualifier("localUserDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        permissions = new EnumMap<>(Permission.class);
        permissions.put(Permission.GET_USER, this::hasSuitableId);
    }

    /**
     * Verify permission method.
     *
     * @param authentication the authentication
     * @param targetObject   the target object
     * @param permission     the permission
     * @return has permission
     */
    public boolean verifyPermission(Authentication authentication, Object targetObject, Object permission) {
        Permission permissionType = definePermission(permission);
        BiPredicate<Authentication, Object> predicate = permissions.get(permissionType);
        return predicate.test(authentication, targetObject);
    }

    private Permission definePermission(Object permission) {
        String permissionName = (String) permission;
        return Permission.definePermission(permissionName);
    }

    private boolean hasSuitableId(Authentication authentication, Object targetObject) {
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
}
