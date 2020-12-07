package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.exception.OrderNotFoundServiceException;
import com.epam.esm.exception.UserNotFoundServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

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
     * @throws GiftCertificateNotFoundServiceException in case of {@code gift
     *                                                 certificate in list
     *                                                 not found by id}
     */
    Order addOrder(@Valid Order order) throws GiftCertificateNotFoundServiceException;

    /**
     * Get list of all user's orders by user login value.
     *
     * @param userId      the user id
     * @param pageable    the pageable
     * @return the orders
     * @throws UserNotFoundServiceException        in case of {@code user with
     *                                             current login not found}
     */
    Page<Order> getUserOrders(Long userId, Pageable pageable) throws UserNotFoundServiceException;

    /**
     * Get user's order by order id.
     *
     * @param id        the order id
     * @return the user's order by id
     * @throws OrderNotFoundServiceException in case of {@code order with
     *                                       current id not found}
     */
    Order getUserOrderById(Long id) throws OrderNotFoundServiceException;
}
