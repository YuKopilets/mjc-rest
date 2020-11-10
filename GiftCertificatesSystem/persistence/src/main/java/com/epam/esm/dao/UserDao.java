package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<Order> findOrdersByLogin(String login);

    Optional<Order> findOrderById(Long id);
}
