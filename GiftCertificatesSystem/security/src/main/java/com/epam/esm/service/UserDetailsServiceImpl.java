package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * The implementation of User details service interface.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see UserDetailsService
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User with login=" + login + " not found!")
        );
        return UserDetailsImpl.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(new String(user.getPassword()))
                .active(user.getActive())
                .grantedAuthorities(new HashSet<>(user.getRoles()))
                .build();
    }
}
