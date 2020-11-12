package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import com.epam.esm.service.exception.PageNumberNotValidServiceException;
import com.epam.esm.service.exception.UserLoginIsNotValidServiceException;
import com.epam.esm.service.exception.OrderNotFoundServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
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
    @Transactional
    public Order addOrder(Order order) {
        order.setDate(LocalDateTime.now());
        order.setCost(calculateOrderCost(order));
        orderDao.save(order);
        addOrderGiftCertificates(order);
        return order;
    }

    @Override
    public List<Order> getUserOrders(String userLogin, int page)
            throws UserLoginIsNotValidServiceException, PageNumberNotValidServiceException {
        validateLogin(userLogin);
        validatePageNumber(page);
        PageRequest pageRequest = new PageRequest(page, PAGINATION_PAGE_SIZE);
        return orderDao.findOrdersByUserLogin(userLogin, pageRequest);
    }

    @Override
    public Order getUserOrderById(Long id) throws OrderNotFoundServiceException, InvalidRequestedIdServiceException {
        validateId(id);
        return orderDao.findById(id).orElseThrow(() -> new OrderNotFoundServiceException("Order with id=" + id
                + " not found!")
        );
    }

    @Override
    public void addOrderGiftCertificates(Order order) {
        orderDao.saveGiftCertificates(order);
    }

    private BigDecimal calculateOrderCost(Order order) {
        return order.getGiftCertificates().stream()
                .map(GiftCertificate::getPrice)
                .reduce(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP), BigDecimal::add);
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
