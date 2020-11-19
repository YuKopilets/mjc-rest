package com.epam.esm.dao.impl;

import com.epam.esm.context.TestConfig;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.NoResultException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
@Sql({"/drop_gift_certificates_system_schema.sql", "/create_gift_certificates_system_schema.sql"})
@Sql(scripts = {"/gift_certificates_system_inserts.sql"})
class UserDaoImplTest {
    @Autowired
    private UserDao userDao;

    @ParameterizedTest
    @MethodSource("prepareUser")
    void findByIdTest(User user) {
        String expected = user.getLogin();
        String actual = userDao.findById(1L).map(User::getLogin).orElse(StringUtils.EMPTY);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdNegativeTest() {
        Optional<User> user = userDao.findById(2L);
        assertFalse(user.isPresent());
    }

    @ParameterizedTest
    @MethodSource("prepareUser")
    void findByLoginTest(User user) {
        Long expected = user.getId();
        Long actual = userDao.findByLogin("user").map(User::getId).orElse(0L);
        assertEquals(expected, actual);
    }

    @Test
    void findByLoginNegativeTest() {
        assertThrows(NoResultException.class, () -> userDao.findByLogin("login"));
    }

    private static Arguments[] prepareUser() {
        User user = User.builder()
                .id(1L)
                .login("user")
                .build();
        return new Arguments[]{Arguments.of(user)};
    }
}