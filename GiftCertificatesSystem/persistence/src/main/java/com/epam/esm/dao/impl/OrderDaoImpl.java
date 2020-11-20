package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractSessionDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.entity.Order;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The type implementation of Order dao.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see OrderDao
 * @see AbstractSessionDao
 */
@Repository
public class OrderDaoImpl extends AbstractSessionDao implements OrderDao {
    private static final String SELECT_BY_LOGIN = "SELECT orders FROM User WHERE login = :login";
    private static final String SELECT_ALL_ORDERS = "SELECT o FROM Order o";
    private static final String USER_ACCOUNT_LOGIN_COLUMN = "login";

    public OrderDaoImpl(LocalSessionFactoryBean localSessionFactory) {
        super(localSessionFactory);
    }

    @Override
    public List<Order> findOrdersByUserLogin(String login, PageRequest pageRequest) {
        // This cast is correct, because the list we're creating is of the same
        // type as the one passed after operation with session. We're selecting
        // the list of orders.
        @SuppressWarnings("unchecked") List<Order> orders = doWithSession(session -> session.createQuery(
                SELECT_BY_LOGIN)
                .setParameter(USER_ACCOUNT_LOGIN_COLUMN, login)
                .setFirstResult(pageRequest.calculateStartElementPosition())
                .setMaxResults(pageRequest.getPageSize())
                .setReadOnly(true)
                .list()
        );
        return orders;
    }

    @Override
    public Order save(Order order) {
        doWithSessionTransaction(session -> session.save(order));
        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        Order order = doWithSession(session -> session.find(Order.class, id));
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> findAll(PageRequest pageRequest) {
        return doWithSession(session -> session.createQuery(SELECT_ALL_ORDERS, Order.class)
                .setFirstResult(pageRequest.calculateStartElementPosition())
                .setMaxResults(pageRequest.getPageSize())
                .setReadOnly(true)
                .list()
        );
    }

    @Override
    public Order update(Order order) {
        return order;
    }

    @Override
    public void delete(Long id) {
        doWithSessionTransaction(session -> {
            Order order = session.find(Order.class, id);
            session.delete(order);
        });
    }
}
