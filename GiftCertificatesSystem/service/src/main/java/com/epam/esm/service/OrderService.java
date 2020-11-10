package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.service.exception.OrderNotFoundServiceException;
import com.epam.esm.service.exception.UserLoginIsNotValidServiceException;

import java.util.List;

public interface OrderService {
    List<Order> getUserOrders(String login) throws UserLoginIsNotValidServiceException;

    Order getUserOrderById(Long id) throws OrderNotFoundServiceException;
}
