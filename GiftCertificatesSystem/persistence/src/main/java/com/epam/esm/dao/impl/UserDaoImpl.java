package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractSessionDao;
import com.epam.esm.dao.ColumnNameConstant;
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

    public UserDaoImpl(LocalSessionFactoryBean localSessionFactory) {
        super(localSessionFactory);
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = doWithSession(session -> session.find(User.class, id));
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        User user = doWithSession(session -> session.createQuery(SELECT_USER_BY_LOGIN, User.class)
                .setParameter(ColumnNameConstant.USER_ACCOUNT_LOGIN, login)
                .setReadOnly(true)
                .getSingleResult()
        );
        return Optional.of(user);
    }
}
