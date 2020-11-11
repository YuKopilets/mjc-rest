package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import com.epam.esm.service.exception.PageNumberNotValidServiceException;
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
    private static final int PAGINATION_PAGE_SIZE = 10;

    private final OrderDao orderDao;

    @Override
    public List<Order> getUserOrders(String userLogin, int page)
            throws UserLoginIsNotValidServiceException, PageNumberNotValidServiceException {
        validateLogin(userLogin);
        validatePageNumber(page);
        PageRequest pageRequest = new PageRequest(page, PAGINATION_PAGE_SIZE);
        return orderDao.findOrdersByLogin(userLogin, pageRequest);
    }

    @Override
    public Order getUserOrderById(Long id) throws OrderNotFoundServiceException, InvalidRequestedIdServiceException {
        validateId(id);
        return orderDao.findOrderById(id).orElseThrow(() -> new OrderNotFoundServiceException("Order with id=" + id
                + " not found!")
        );
    }

    private void validateLogin(String login) {
        int length = login.length();
        if (!(length >= 4 && length <= 50)) {
            throw new UserLoginIsNotValidServiceException(login
                    + " is not valid. Required login size not less 4 chars and not more then 50");
        }
    }

    private void validateId(Long id) throws InvalidRequestedIdServiceException {
        if (id <= 0) {
            throw new InvalidRequestedIdServiceException("Order id: " + id
                    + " does not fit the allowed gap. Expected gap: id > 0");
        }
    }

    private void validatePageNumber(int page) {
        if (page < 0) {
            throw new PageNumberNotValidServiceException("Orders can't be load. " + page
                    + " is not valid value. Page must be positive number");
        }
    }
}
