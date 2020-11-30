package com.epam.esm.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;

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
        return Arrays.stream(Permission.values())
                .filter(value -> value.getPermissionName().equals(permissionName))
                .findFirst()
                .map(value -> value.hasPermission(authentication, targetId, userDetailsService))
                .orElse(false);
    }
}
