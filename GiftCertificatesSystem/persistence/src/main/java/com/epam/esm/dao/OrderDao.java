package com.epam.esm.dao;

import com.epam.esm.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    List<Order> findOrdersByLogin(String login);

    Optional<Order> findOrderById(Long id);
}
