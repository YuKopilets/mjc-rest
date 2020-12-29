package com.epam.esm.service.impl;

import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.OrderRepository;
import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.service.exception.UserNotFoundServiceException;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.OrderNotFoundServiceException;
import com.epam.esm.security.util.AuthenticationUtils;
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
    public Order addOrder(@Valid Order order) throws GiftCertificateNotFoundServiceException {
        Long userId = getAuthorizedUserId();
        order.setUserId(userId);
        order.setDate(LocalDateTime.now());
        initOrderByGiftCertificates(order);
        order.setCost(calculateOrderCost(order));
        orderRepository.save(order);
        return order;
    }

    @Override
    public Page<Order> getUserOrders(Long userId, Pageable pageable) throws UserNotFoundServiceException {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundServiceException(userId));
        return orderRepository.findByUserId(userId, pageable);
    }

    @Override
    public Order getUserOrderById(Long id) throws OrderNotFoundServiceException {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundServiceException(id));
    }

    private Long getAuthorizedUserId() {
        return AuthenticationUtils.getAuthorizedUserId();
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
