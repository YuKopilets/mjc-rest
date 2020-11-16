package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import com.epam.esm.service.exception.OrderNotFoundServiceException;
import com.epam.esm.service.exception.PageNumberNotValidServiceException;
import com.epam.esm.service.exception.PageSizeNotValidServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private final GiftCertificateDao giftCertificateDao;

    @Override
    @Transactional
    public Order addOrder(Order order) throws GiftCertificateNotFoundServiceException {
        order.setDate(LocalDateTime.now());
        prepareOrderGiftCertificates(order);
        order.setCost(calculateOrderCost(order));
        orderDao.save(order);
        addOrderGiftCertificates(order);
        return order;
    }

    @Override
    public List<Order> getUserOrders(String userLogin, int page, int pageSize)
            throws PageNumberNotValidServiceException, PageSizeNotValidServiceException {
        validatePageNumber(page);
        validatePageSize(pageSize);
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return orderDao.findOrdersByUserLogin(userLogin, pageRequest);
    }

    @Override
    public Order getUserOrderById(Long id) throws OrderNotFoundServiceException, InvalidRequestedIdServiceException {
        validateId(id);
        return orderDao.findById(id).orElseThrow(() -> new OrderNotFoundServiceException("Order with id=" + id
                + " not found!")
        );
    }

    private void addOrderGiftCertificates(Order order) {
        orderDao.saveGiftCertificates(order);
    }

    @Override
    public void reviewOrdersCost() {
        long countOfOrders = orderDao.countOrders();
        final int pageSize = 50;
        IntStream.range(1, countPages(countOfOrders, pageSize) + 1)
                .mapToObj(i -> new PageRequest(i, pageSize))
                .map(pageRequest -> recalculateOrdersCost(orderDao.findAll(pageRequest)))
                .forEach(this::updateOrdersCost);
    }

    private void prepareOrderGiftCertificates(Order order) throws GiftCertificateNotFoundServiceException {
        order.setGiftCertificates(
                order.getGiftCertificates().stream()
                        .map(this::findOrderGiftCertificate)
                        .collect(Collectors.toList())
        );
    }

    private GiftCertificate findOrderGiftCertificate(GiftCertificate giftCertificate) {
        return giftCertificateDao.findById(giftCertificate.getId()).orElseThrow(() ->
                new GiftCertificateNotFoundServiceException("Gift certificate with id="
                        + giftCertificate.getId() + " not found!")
        );
    }

    private BigDecimal calculateOrderCost(Order order) {
        return order.getGiftCertificates().stream()
                .map(GiftCertificate::getPrice)
                .reduce(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP), BigDecimal::add);
    }

    private int countPages(long countOfOrders, int pageSize) {
        return (int) (countOfOrders % pageSize == 0 ? countOfOrders / pageSize : countOfOrders / pageSize + 1);
    }

    private List<Order> recalculateOrdersCost(List<Order> orders) {
        orders.stream()
                .filter(order -> order.getCost().doubleValue() == 0.0)
                .forEach(order -> order.setCost(calculateOrderCost(order)));
        return orders;
    }

    private void updateOrdersCost(List<Order> orders) {
        orders.forEach(orderDao::update);
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

    private void validatePageSize(int size) {
        if (size < 0) {
            throw new PageSizeNotValidServiceException("Orders can't be load. " + size
                    + " is not valid value. Page must be positive number");
        }
    }
}
