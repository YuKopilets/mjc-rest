package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractSessionDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl extends AbstractSessionDao implements OrderDao {
    private static final String SELECT_BY_LOGIN = "SELECT orders FROM User WHERE login = :login";

    public OrderDaoImpl(LocalSessionFactoryBean localSessionFactory) {
        super(localSessionFactory);
    }

    @Override
    public List<Order> findOrdersByLogin(String login) {
        return doWithSession(session -> session.createQuery(SELECT_BY_LOGIN)
                .setParameter("login", login)
                .setReadOnly(true)
                .list()
        );
    }

    @Override
    public Optional<Order> findOrderById(Long id) {
        return Optional.ofNullable(doWithSession(session -> session.find(Order.class, id)));
    }
}
