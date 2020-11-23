package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.Optional;

/**
 * The interface User dao for get operations with <i>user_account</i> table
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
public interface UserDao {
    /**
     * Find user by id.
     *
     * @param id the id
     * @return the optional
     */
    Optional<User> findById(Long id);

    /**
     * Find user by login.
     *
     * @param login the login
     * @return the optional
     */
    Optional<User> findByLogin(String login);
}
