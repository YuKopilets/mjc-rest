package com.epam.esm.security.evaluator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
}
