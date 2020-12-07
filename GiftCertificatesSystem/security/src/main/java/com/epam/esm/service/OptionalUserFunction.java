package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;

import java.util.Optional;

/**
 * Represents a function that accepts two arguments
 * and produces optional user.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(UserRepository, String)}.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see FunctionalInterface
 */
@FunctionalInterface
public interface OptionalUserFunction {
    /**
     * Applies this function to the given argument.
     *
     * @param userRepository the user repository
     * @param sub            the oauth sub attribute
     * @return the optional user
     */
    Optional<User> apply(UserRepository userRepository, String sub);
}
