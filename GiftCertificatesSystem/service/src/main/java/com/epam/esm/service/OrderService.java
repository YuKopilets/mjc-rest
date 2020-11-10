package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.service.exception.OrderNotFoundServiceException;
import com.epam.esm.service.exception.UserLoginIsNotValidServiceException;

import java.util.List;

/**
 * The interface Order service.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
public interface OrderService {
    /**
     * Get list of all user's orders by user login value.
     *
     * @param userLogin the user login
     * @return the orders
     * @throws UserLoginIsNotValidServiceException in case of {@code the user
     * login is not valid}
     */
    List<Order> getUserOrders(String userLogin) throws UserLoginIsNotValidServiceException;

    /**
     * Get user's order by order id.
     *
     * @param id the order id
     * @return the user's order by id
     * @throws OrderNotFoundServiceException in case of {@code order with
     * current id not found}
     */
    Order getUserOrderById(Long id) throws OrderNotFoundServiceException;
}
