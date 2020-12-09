package com.epam.esm.service;

import com.epam.esm.entity.LocalUser;
import com.epam.esm.entity.User;
import com.epam.esm.exception.UserNotFoundServiceException;

/**
 * The interface User service.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
public interface UserService {
    /**
     * Sing up process. Add new local user.
     *
     * @param user the user
     */
    void signUp(LocalUser user);

    /**
     * Get user by current id.
     *
     * @param id the id
     * @return the user by id
     * @throws UserNotFoundServiceException in case of {@code user with
     *                                      current id not found}
     */
    User getUserById(Long id) throws UserNotFoundServiceException;
}
