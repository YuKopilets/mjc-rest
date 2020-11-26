package com.epam.esm.service.impl;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.UserNotFoundServiceException;
import com.epam.esm.service.OrderService;
import com.epam.esm.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.exception.OrderNotFoundServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository giftCertificateRepository;

    @Override
    @Transactional
    public Order addOrder(@Valid Order order) throws GiftCertificateNotFoundServiceException,
            UserNotFoundServiceException {
        userRepository.findById(order.getUserId()).orElseThrow(() -> new UserNotFoundServiceException(order.getUserId()));

        order.setDate(LocalDateTime.now());
        initOrderByGiftCertificates(order);
        order.setCost(calculateOrderCost(order));
        orderRepository.save(order);
        return order;
    }

    @Override
    public Page<Order> getUserOrders(String userLogin, Pageable pageable) throws UserNotFoundServiceException {
        userRepository.findByLogin(userLogin).orElseThrow(() -> new UserNotFoundServiceException(userLogin));
        return orderRepository.findOrdersByUserLogin(userLogin, pageable);
    }

    @Override
    public Order getUserOrderById(Long id) throws OrderNotFoundServiceException {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundServiceException(id));
    }

    private void initOrderByGiftCertificates(Order order) throws GiftCertificateNotFoundServiceException {
        List<GiftCertificate> giftCertificates = order.getGiftCertificates().stream()
                .map(this::findOrderGiftCertificate)
                .collect(Collectors.toList());
        order.setGiftCertificates(giftCertificates);
    }

    private GiftCertificate findOrderGiftCertificate(GiftCertificate giftCertificate)
            throws GiftCertificateNotFoundServiceException {
        return giftCertificateRepository.findById(giftCertificate.getId())
                .orElseThrow(() -> new GiftCertificateNotFoundServiceException(giftCertificate.getId()));
    }

    private BigDecimal calculateOrderCost(Order order) {
        return order.getGiftCertificates().stream()
                .map(GiftCertificate::getPrice)
                .reduce(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP), BigDecimal::add);
    }
}
