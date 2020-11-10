package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.UserLoginIsNotValidServiceException;
import com.epam.esm.service.exception.OrderNotFoundServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type implementation of Order service.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see OrderService
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;

    @Override
    public List<Order> getUserOrders(String userLogin) throws UserLoginIsNotValidServiceException {
        if (loginIsValid(userLogin)) {
            return orderDao.findOrdersByLogin(userLogin);
        } else {
            throw new UserLoginIsNotValidServiceException(userLogin
                    + " is not valid. Required login size not less 4 chars and not more then 50");
        }
    }

    @Override
    public Order getUserOrderById(Long id) throws OrderNotFoundServiceException {
        return orderDao.findOrderById(id).orElseThrow(() -> new OrderNotFoundServiceException("Order with id=" + id
                + " not found!")
        );
    }

    private boolean loginIsValid(String login) {
        int length = login.length();
        return length >= 4 && length <= 50;
    }
}
