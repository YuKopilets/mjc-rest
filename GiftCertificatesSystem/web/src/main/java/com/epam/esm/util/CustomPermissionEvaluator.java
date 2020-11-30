package com.epam.esm.util;

import com.epam.esm.service.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * The {@code Custom permission evaluator} is implementation of Permission
 * Evaluator for creating custom verification of authorized users permissions.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see PermissionEvaluator
 */
@Component
@RequiredArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {
    private final UserDetailsService userDetailsService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
                                 Object permission) {
        String permissionName = (String) permission;
        switch (permissionName) {
            case "hasPermissionToGetUser":
                return hasSuitableId(authentication, (Long) targetId);
        }
        return false;
    }

    private boolean hasSuitableId(Authentication authentication, Long id) {
        String login = authentication.getName();
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(login);
        return userDetails.getId().equals(id);
    }
}
