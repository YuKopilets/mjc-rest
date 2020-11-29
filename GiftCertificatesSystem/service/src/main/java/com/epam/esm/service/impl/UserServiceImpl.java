package com.epam.esm.service.impl;

import com.epam.esm.entity.UserRole;
import com.epam.esm.exception.RegistrationFailServiceException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.entity.User;
import com.epam.esm.exception.UserNotFoundServiceException;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Collections;

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
    private final PasswordEncoder passwordEncoder;

    @Override
    public void singUp(User user) {
        userRepository.findByLogin(user.getLogin()).ifPresent(userByLogin -> {
            throw new RegistrationFailServiceException("User with login: "
                    + userByLogin.getLogin() + " already exists");
        });
        String encodedPassword = passwordEncoder.encode(CharBuffer.wrap(user.getPassword()));
        user.setPassword(encodedPassword.toCharArray());
        user.setRoles(Collections.singleton(UserRole.USER));
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'userId', 'hasPermissionToGetUser') or hasRole('ADMIN')")
    public User getUserById(Long id) throws UserNotFoundServiceException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundServiceException(id));
    }

    @Override
    @PreAuthorize("#login == authentication.principal.username or hasRole('ADMIN')")
    public User getUserByLogin(String login) throws UserNotFoundServiceException {
        return userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundServiceException(login));
    }
}
