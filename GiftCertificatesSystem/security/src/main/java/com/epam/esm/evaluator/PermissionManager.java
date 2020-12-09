package com.epam.esm.evaluator;

import org.springframework.security.core.Authentication;

import java.util.function.BiPredicate;

/**
 * The interface Permission manager.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see BiPredicate
 */
public interface PermissionManager extends BiPredicate<Authentication, Object> {
    /**
     * Get permission name.
     *
     * @return the permission name
     */
    String getPermissionName();
}
