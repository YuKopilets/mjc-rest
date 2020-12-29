package com.epam.esm.persistence.repository;

import com.epam.esm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
     * Find local user by login.
     *
     * @param login the login
     * @return the optional user
     */
    @Query("SELECT lu FROM LocalUser lu WHERE lu.login = :login")
    Optional<User> findLocalUserByLogin(String login);

    /**
     * Find google user by oauth sub.
     *
     * @param sub the sub
     * @return the optional google user
     */
    @Query("SELECT gu FROM OAuth2GoogleUser gu WHERE gu.sub = :sub")
    Optional<User> findGoogleUserBySub(String sub);

    /**
     * Find github user by oauth sub.
     *
     * @param sub the sub
     * @return the optional github user
     */
    @Query("SELECT ghu FROM OAuth2GithubUser ghu WHERE ghu.sub = :sub")
    Optional<User> findGithubUserBySub(String sub);
}
