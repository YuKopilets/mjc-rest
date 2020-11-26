package com.epam.esm.repository;

import com.epam.esm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface User repository for get operations with
 * <i>user_account</i> table
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find user by login.
     *
     * @param login the login
     * @return the optional
     */
    Optional<User> findByLogin(String login);
}
