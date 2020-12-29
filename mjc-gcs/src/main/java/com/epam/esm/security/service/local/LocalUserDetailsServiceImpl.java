package com.epam.esm.security.service.local;

import com.epam.esm.entity.LocalUser;
import com.epam.esm.persistence.repository.UserRepository;
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
public class LocalUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        LocalUser user = (LocalUser) userRepository.findLocalUserByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User with login=" + login + " not found!")
        );
        return LocalUserDetailsImpl.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(new String(user.getPassword()))
                .active(user.getActive())
                .grantedAuthorities(new HashSet<>(user.getRoles()))
                .build();
    }
}
