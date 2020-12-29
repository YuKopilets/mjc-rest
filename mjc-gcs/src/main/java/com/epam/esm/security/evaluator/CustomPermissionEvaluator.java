package com.epam.esm.security.evaluator;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
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
    private final PermissionVerifier permissionVerifier;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return permissionVerifier.verifyPermission(authentication, targetDomainObject, permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
                                 Object permission) {
        return permissionVerifier.verifyPermission(authentication, targetId, permission);
    }
}
