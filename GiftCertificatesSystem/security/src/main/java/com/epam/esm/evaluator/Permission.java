package com.epam.esm.evaluator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum Permission {
    GET_USER("hasPermissionToGetUser");

    @Getter
    private final String permissionName;

    public static Permission definePermission(String permissionName) {
        return Arrays.stream(Permission.values())
                .filter(value -> value.getPermissionName().equals(permissionName))
                .findFirst()
                .orElse(GET_USER);
    }
}
