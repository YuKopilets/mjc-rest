package com.epam.esm.dao;

import com.epam.esm.entity.Order;

import java.util.List;
import java.util.Optional;

/**
 * The interface Order dao is expansion of CrudDao for additional read
 * operations with <i>user_account</i> table, <i>user_order</i> table and
 * <i>order_has_gift_certificate</i> associated table.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see CrudDao
 */
public interface OrderDao {
    /**
     * Find list of orders by user login. The {@code page request}
     * can show which part of list needed to return.
     * Read operation (CRUD).
     *
     * @param login the login
     * @param pageRequest  the number and size of page
     * @return the list
     */
    List<Order> findOrdersByLogin(String login, PageRequest pageRequest);

    /**
     * Find order by id.
     * Read operation (CRUD).
     *
     * @param id the id
     * @return the optional order
     */
    Optional<Order> findOrderById(Long id);
}
