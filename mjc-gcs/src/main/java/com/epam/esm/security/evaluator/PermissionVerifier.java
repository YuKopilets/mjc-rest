package com.epam.esm.security.evaluator;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * The {@code Permission verifier} verifies custom permission.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class PermissionVerifier {
    private final List<PermissionManager> permissionManagers;

    /**
     * Verify permission method.
     *
     * @param authentication the authentication
     * @param targetObject   the target object
     * @param permission     the permission
     * @return has permission
     */
    public boolean verifyPermission(Authentication authentication, Object targetObject, Object permission) {
        Optional<PermissionManager> permissionManager = definePermissionManager(permission);
        return permissionManager.map(manager -> manager.test(authentication, targetObject)).orElse(false);
    }

    private Optional<PermissionManager> definePermissionManager(Object permission) {
        String permissionName = (String) permission;
        return permissionManagers.stream()
                .filter(permissionManager -> permissionManager.getPermissionName().equals(permissionName))
                .findFirst();
    }
}
