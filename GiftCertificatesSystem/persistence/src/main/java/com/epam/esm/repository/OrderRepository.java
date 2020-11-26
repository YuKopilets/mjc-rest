package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * The interface Order repository is expansion of CrudDao for additional read
 * operations with <i>user_account</i> table, <i>user_order</i> table and
 * <i>order_has_gift_certificate</i> associated table.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see JpaRepository
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Find list of orders by user login. The {@code page request}
     * can show which part of list needed to return.
     * Read operation (CRUD).
     *
     * @param login the user login
     * @return the list
     */
    @Query("SELECT u.orders FROM User u WHERE u.login = :login")
    Page<Order> findOrdersByUserLogin(String login, Pageable pageable);
}
