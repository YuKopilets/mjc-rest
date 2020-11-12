package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import com.epam.esm.service.exception.OrderNotFoundServiceException;
import com.epam.esm.service.exception.PageNumberNotValidServiceException;
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
     * Add new order.
     *
     * @param order the order
     * @return the order
     */
    Order addOrder(Order order);

    /**
     * Get list of all user's orders by user login value.
     *
     * @param userLogin the user login
     * @param page      the page number
     * @return the orders
     * @throws UserLoginIsNotValidServiceException in case of {@code the user
     *                                             login is not valid}
     * @throws InvalidRequestedIdServiceException  in case of {@code id is
     * not valid to do operation}
     * @throws PageNumberNotValidServiceException in case of {@code page
     * number is not valid to get list}
     */
    List<Order> getUserOrders(String userLogin, int page) throws UserLoginIsNotValidServiceException,
            InvalidRequestedIdServiceException, PageNumberNotValidServiceException;

    /**
     * Get user's order by order id.
     *
     * @param id the order id
     * @return the user's order by id
     * @throws OrderNotFoundServiceException in case of {@code order with
     * current id not found}
     */
    Order getUserOrderById(Long id) throws OrderNotFoundServiceException;

    /**
     * Add order gift certificates.
     *
     * @param order the order with required gift certificates to add
     */
    void addOrderGiftCertificates(Order order);
}
