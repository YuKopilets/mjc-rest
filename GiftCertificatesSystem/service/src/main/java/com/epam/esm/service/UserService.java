package com.epam.esm.service;

import com.epam.esm.entity.Order;

import java.util.List;

public interface UserService {
    List<Order> getUserOrders(String login);

    Order getUserOrderById(Long id);
}
