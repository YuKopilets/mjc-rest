package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.entity.User;
import com.epam.esm.exception.UserNotFoundServiceException;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    @Mock
    private UserDao userDao;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userDao = Mockito.mock(UserDaoImpl.class);
        userService = new UserServiceImpl(userDao);
    }

    @AfterEach
    void tearDown() {
        userDao = null;
        userService = null;
    }

    @ParameterizedTest
    @MethodSource("prepareUser")
    void getUserByIdTest(User expected) {
        Optional<User> userOptional = Optional.of(expected);
        Mockito.when(userDao.findById(1L)).thenReturn(userOptional);
        User actual = userService.getUserById(1L);
        assertEquals(expected, actual);
    }

    @Test
    void getUserByIdNegativeTest() {
        Mockito.when(userDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundServiceException.class, () -> userService.getUserById(1L));
    }

    @ParameterizedTest
    @MethodSource("prepareUser")
    void getUserByLoginTest(User expected) {
        Optional<User> userOptional = Optional.of(expected);
        Mockito.when(userDao.findByLogin("login")).thenReturn(userOptional);
        User actual = userService.getUserByLogin("login");
        assertEquals(expected, actual);
    }

    @Test
    void getUserByLoginNegativeTest() {
        Mockito.when(userDao.findByLogin("login")).thenReturn(Optional.empty());
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