package com.epam.esm.service.impl;

import com.epam.esm.repository.UserRepository;
import com.epam.esm.entity.User;
import com.epam.esm.exception.UserNotFoundServiceException;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The type implementation of User service.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see UserService
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) throws UserNotFoundServiceException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundServiceException(id));
    }

    @Override
    public User getUserByLogin(String login) throws UserNotFoundServiceException {
        return userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundServiceException(login));
    }
}
