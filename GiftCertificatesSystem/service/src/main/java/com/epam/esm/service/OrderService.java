package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import com.epam.esm.service.exception.OrderNotFoundServiceException;
import com.epam.esm.service.exception.PageNumberNotValidServiceException;
import com.epam.esm.service.exception.PageSizeNotValidServiceException;

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
     * @throws PageNumberNotValidServiceException in case of {@code page
     *                                            number is not valid to get list}
     * @throws PageSizeNotValidServiceException   in case of {@code page size
     *                                            is not valid to get list}
     */
    List<Order> getUserOrders(String userLogin, int page, int pageSize) throws PageNumberNotValidServiceException,
            PageSizeNotValidServiceException;

    /**
     * Get user's order by order id.
     *
     * @param id the order id
     * @return the user's order by id
     * @throws OrderNotFoundServiceException      in case of {@code order with
     *                                            current id not found}
     * @throws InvalidRequestedIdServiceException in case of {@code id is not
     *                                            valid to do operation}
     */
    Order getUserOrderById(Long id) throws OrderNotFoundServiceException, InvalidRequestedIdServiceException;

    /**
     * Scan orders without calculated cost and calculate it for them.
     */
    void reviewOrdersCost();
}
