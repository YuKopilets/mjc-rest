package com.epam.esm.dao;

import com.epam.esm.entity.Order;

import java.util.List;

/**
 * The interface Order dao is expansion of CrudDao for additional read
 * operations with <i>user_account</i> table, <i>user_order</i> table and
 * <i>order_has_gift_certificate</i> associated table.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see CrudDao
 */
public interface OrderDao extends CrudDao<Order> {
    /**
     * Find list of orders by user login. The {@code page request}
     * can show which part of list needed to return.
     * Read operation (CRUD).
     *
     * @param login the user login
     * @param pageRequest  the number and size of page
     * @return the list
     */
    List<Order> findOrdersByUserLogin(String login, PageRequest pageRequest);

    /**
     * Save gift certificates to <i>order_has_gift_certificate</i> table.
     * Create operation (CRUD).
     *
     * @param order the order with certificates
     */
    void saveGiftCertificates(Order order);
}
