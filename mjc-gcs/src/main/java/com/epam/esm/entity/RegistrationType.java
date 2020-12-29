package com.epam.esm.entity;

/**
 * The {@code type UserRole} is domain representation of
 * <i>registration_type</i> table.
 * It used for mapping to relational database.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
public enum RegistrationType {
    LOCAL,
    GOOGLE,
    GITHUB
}
