package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractSessionDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl extends AbstractSessionDao implements UserDao {
    private static final String SELECT_BY_LOGIN = "FROM User WHERE login = :login";

    public UserDaoImpl(LocalSessionFactoryBean localSessionFactory) {
        super(localSessionFactory);
    }

    @Override
    public List<Order> findOrdersByLogin(String login) {
        return doWithSession(session -> {
            User user = (User) session.createQuery(SELECT_BY_LOGIN)
                            .setParameter("login", login)
                            .setReadOnly(true)
                            .getSingleResult();
            return user.getOrders();
        });
    }
}
