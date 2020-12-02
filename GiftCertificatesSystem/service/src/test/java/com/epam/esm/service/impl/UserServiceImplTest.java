package com.epam.esm.service.impl;

import com.epam.esm.entity.User;
import com.epam.esm.exception.UserNotFoundServiceException;
import com.epam.esm.repository.UserRepository;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, new BCryptPasswordEncoder(8));
    }

    @AfterEach
    void tearDown() {
        userRepository = null;
        userService = null;
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

    @ParameterizedTest
    @MethodSource("prepareUser")
    void getUserByLoginTest(User expected) {
        Optional<User> userOptional = Optional.of(expected);
        Mockito.when(userRepository.findByLogin("login")).thenReturn(userOptional);
        User actual = userService.getUserByLogin("login");
        assertEquals(expected, actual);
    }

    @Test
    void getUserByLoginNegativeTest() {
        Mockito.when(userRepository.findByLogin("login")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundServiceException.class, () -> userService.getUserByLogin("login"));
    }

    private static Arguments[] prepareUser() {
        User user = User.builder()
                .id(1L)
                .login("login")
                .build();
        return new Arguments[]{Arguments.of(user)};
    }
}