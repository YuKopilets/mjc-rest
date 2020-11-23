package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractSessionDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The type implementation of User dao.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see UserDao
 * @see AbstractSessionDao
 */
@Repository
public class UserDaoImpl extends AbstractSessionDao implements UserDao {
    private static final String SELECT_USER_BY_LOGIN = "SELECT u FROM User u WHERE u.login = :login";
    private static final String USER_ACCOUNT_LOGIN_COLUMN = "login";

    public UserDaoImpl(LocalSessionFactoryBean localSessionFactory) {
        super(localSessionFactory);
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = doWithSession(session -> session.find(User.class, id));
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        User user = doWithSession(session -> session.createQuery(SELECT_USER_BY_LOGIN, User.class)
                .setParameter(USER_ACCOUNT_LOGIN_COLUMN, login)
                .setReadOnly(true)
                .getSingleResult()
        );
        return Optional.ofNullable(user);
    }
}
