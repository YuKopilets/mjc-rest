package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.UserNotFoundServiceException;
import com.epam.esm.service.OrderService;
import com.epam.esm.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.exception.OrderNotFoundServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type implementation of Order service.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see OrderService
 */
@Service
@Validated
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final GiftCertificateDao giftCertificateDao;

    @Override
    public Order addOrder(@Valid Order order) throws GiftCertificateNotFoundServiceException,
            UserNotFoundServiceException {
        userDao.findById(order.getUserId()).orElseThrow(() -> new UserNotFoundServiceException(order.getUserId()));

        order.setDate(LocalDateTime.now());
        initOrderByGiftCertificates(order);
        order.setCost(calculateOrderCost(order));
        orderDao.save(order);
        return order;
    }

    @Override
    public List<Order> getUserOrders(String userLogin, PageRequest pageRequest) throws UserNotFoundServiceException {
        userDao.findByLogin(userLogin).orElseThrow(() -> new UserNotFoundServiceException(userLogin));
        return orderDao.findOrdersByUserLogin(userLogin, pageRequest);
    }

    @Override
    public Order getUserOrderById(Long id) throws OrderNotFoundServiceException {
        return orderDao.findById(id).orElseThrow(() -> new OrderNotFoundServiceException(id));
    }

    private void initOrderByGiftCertificates(Order order) throws GiftCertificateNotFoundServiceException {
        final List<GiftCertificate> giftCertificates = order.getGiftCertificates().stream()
                .map(this::findOrderGiftCertificate)
                .collect(Collectors.toList());
        order.setGiftCertificates(giftCertificates);
    }

    private GiftCertificate findOrderGiftCertificate(GiftCertificate giftCertificate) {
        return giftCertificateDao.findById(giftCertificate.getId()).orElseThrow(() ->
                new GiftCertificateNotFoundServiceException(giftCertificate.getId())
        );
    }

    private BigDecimal calculateOrderCost(Order order) {
        return order.getGiftCertificates().stream()
                .map(GiftCertificate::getPrice)
                .reduce(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP), BigDecimal::add);
    }
}
