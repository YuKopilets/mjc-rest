package com.epam.esm.service.impl;

import com.epam.esm.entity.LocalUser;
import com.epam.esm.entity.User;
import com.epam.esm.service.exception.RegistrationFailServiceException;
import com.epam.esm.service.exception.UserNotFoundServiceException;
import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.CharBuffer;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        userService = Mockito.spy(new UserServiceImpl(userRepository, passwordEncoder));
    }

    @AfterEach
    void tearDown() {
        userRepository = null;
        userService = null;
    }

    @ParameterizedTest
    @MethodSource("prepareLocalUser")
    void signUpTest(LocalUser localUser, CharBuffer charBuffer) {
        Mockito.when(userRepository.findLocalUserByLogin("login")).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(charBuffer)).thenReturn("pass");
        userService.signUp(localUser);
        Mockito.verify(userRepository).save(localUser);
    }

    @ParameterizedTest
    @MethodSource("prepareLocalUser")
    void signUpNegativeTest(LocalUser localUser) {
        Mockito.when(userRepository.findLocalUserByLogin("login")).thenReturn(Optional.ofNullable(localUser));
        assertThrows(RegistrationFailServiceException.class, () -> userService.signUp(localUser));
    }

    @ParameterizedTest
    @MethodSource("prepareUser")
    void getUserByIdTest(User expected) {
        Optional<User> userOptional = Optional.of(expected);
        Mockito.when(userRepository.findById(1L)).thenReturn(userOptional);
        User actual = userService.getUserById(1L);
        assertEquals(expected, actual);
    }

    @Test
    void getUserByIdNegativeTest() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundServiceException.class, () -> userService.getUserById(1L));
    }

    private static Arguments[] prepareUser() {
        User user = User.builder()
                .id(1L)
                .build();
        return new Arguments[]{Arguments.of(user)};
    }

    private static Arguments[] prepareLocalUser() {
        char[] password = new char[]{'p', 'a', 's', 's'};
        LocalUser localUser = LocalUser.builder()
                .login("login")
                .password(password)
                .build();
        return new Arguments[]{Arguments.of(localUser, CharBuffer.wrap(password))};
    }
}