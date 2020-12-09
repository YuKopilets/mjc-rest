package com.epam.esm.evaluator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * The enum Permission contains custom permissions types.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@RequiredArgsConstructor
public enum Permission {
    GET_USER("getUserById");

    @Getter
    private final String permissionName;

    /**
     * Define permission type.
     *
     * @param permissionName the permission name
     * @return the permission type
     */
    public static Permission definePermission(String permissionName) {
        return Arrays.stream(Permission.values())
                .filter(value -> value.getPermissionName().equals(permissionName))
                .findFirst()
                .orElse(GET_USER);
    }
}
