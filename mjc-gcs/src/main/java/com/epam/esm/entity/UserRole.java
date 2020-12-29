package com.epam.esm.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * The {@code type UserRole} is domain representation of
 * <i>user_role</i> table.
 * It used for mapping to relational database.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see GrantedAuthority
 */
@RequiredArgsConstructor
public enum UserRole implements GrantedAuthority {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String authorityName;

    @Override
    public String getAuthority() {
        return this.authorityName;
    }
}
