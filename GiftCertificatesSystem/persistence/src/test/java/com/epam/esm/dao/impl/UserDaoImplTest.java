package com.epam.esm.dao.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
@Sql({"/drop_gift_certificates_system_schema.sql", "/create_gift_certificates_system_schema.sql"})
@Sql(scripts = {"/gift_certificates_system_inserts.sql"})
class UserDaoImplTest {
    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @MethodSource("prepareUser")
    void findByIdTest(User user) {
        String expected = user.getLogin();
        String actual = userRepository.findById(1L).map(User::getLogin).orElse(StringUtils.EMPTY);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdNegativeTest() {
        Optional<User> user = userRepository.findById(2L);
        assertFalse(user.isPresent());
    }

    @ParameterizedTest
    @MethodSource("prepareUser")
    void findByLoginTest(User user) {
        Long expected = user.getId();
        Long actual = userRepository.findByLogin("user").map(User::getId).orElse(0L);
        assertEquals(expected, actual);
    }

    private static Arguments[] prepareUser() {
        User user = User.builder()
                .id(1L)
                .login("user")
                .build();
        return new Arguments[]{Arguments.of(user)};
    }
}