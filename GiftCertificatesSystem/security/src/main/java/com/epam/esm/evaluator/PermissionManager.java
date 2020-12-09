package com.epam.esm.evaluator;

import org.springframework.security.core.Authentication;

import java.util.function.BiPredicate;

public interface PermissionManager extends BiPredicate<Authentication, Object> {
    String getPermissionName();
}
