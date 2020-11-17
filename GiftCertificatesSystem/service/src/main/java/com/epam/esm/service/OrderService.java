package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.OrderNotFoundServiceException;

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
     * @throws GiftCertificateNotFoundServiceException in case of {@code gift
     *                                                 certificate in list not found by id}
     */
    Order addOrder(Order order) throws GiftCertificateNotFoundServiceException;

    /**
     * Get list of all user's orders by user login value.
     *
     * @param userLogin the user login
     * @param page      the page number
     * @param pageSize  the page size
     * @return the orders
     */
    List<Order> getUserOrders(String userLogin, int page, int pageSize);

    /**
     * Get user's order by order id.
     *
     * @param id the order id
     * @return the user's order by id
     * @throws OrderNotFoundServiceException      in case of {@code order with
     *                                            current id not found}
     */
    Order getUserOrderById(Long id) throws OrderNotFoundServiceException;

    /**
     * Scan orders without calculated cost and calculate it for them.
     */
    void reviewOrdersCost();
}
