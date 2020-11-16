package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.PageNumberNotValidServiceException;
import com.epam.esm.service.exception.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    @Mock
    private OrderDao orderDao;
    @Mock
    private GiftCertificateDao giftCertificateDao;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderDao = Mockito.mock(OrderDaoImpl.class);
        giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
        orderService = new OrderServiceImpl(orderDao, giftCertificateDao);
    }

    @AfterEach
    void tearDown() {
        orderDao = null;
        giftCertificateDao = null;
        orderService = null;
    }

    @ParameterizedTest
    @MethodSource("prepareOrder")
    void addOrderTest(Order order) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setPrice(BigDecimal.valueOf(0));
        Optional<GiftCertificate> optionalCertificate = Optional.of(giftCertificate);
        Mockito.when(giftCertificateDao.findById(1L)).thenReturn(optionalCertificate);
        Mockito.when(giftCertificateDao.findById(2L)).thenReturn(optionalCertificate);
        orderService.addOrder(order);
        Mockito.verify(orderDao).save(order);
    }

    @ParameterizedTest
    @MethodSource("prepareOrder")
    void addOrderNegativeTest(Order order) {
        assertThrows(GiftCertificateNotFoundServiceException.class, ()-> orderService.addOrder(order));
    }

    @ParameterizedTest
    @MethodSource("prepareOrders")
    void getUserOrdersTest(List<Order> exceptedOrders) {
        PageRequest pageRequest = new PageRequest(1, 10);
        Mockito.when(orderDao.findOrdersByUserLogin(Mockito.eq("login"), Mockito.eq(pageRequest)))
                .thenReturn(exceptedOrders);
        List<Order> actualOrders = orderService.getUserOrders("login", 1, 10);
        assertEquals(exceptedOrders, actualOrders);
    }

    @Test
    void getUserOrdersNegativeTest() {
        assertThrows(PageNumberNotValidServiceException.class, () ->
                orderService.getUserOrders("log", -1, 10));
    }

    @ParameterizedTest
    @MethodSource("prepareOrder")
    void getUserOrderByIdTest(Order order) {
        Optional<Order> orderOptional = Optional.ofNullable(order);
        Mockito.when(orderDao.findById(1L)).thenReturn(orderOptional);
        orderService.getUserOrderById(1L);
        Mockito.verify(orderDao, Mockito.atLeastOnce()).findById(Mockito.anyLong());
    }

    @Test
    void getUserOrderByIdNegativeTest() {
        Mockito.when(orderDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> orderService.getUserOrderById(1L));
    }

    @ParameterizedTest
    @MethodSource("prepareOrders")
    void reviewOrdersCost(List<Order> orders) {
        PageRequest pageRequest = new PageRequest(1, 50);
        Mockito.when(orderDao.countOrders()).thenReturn(50L);
        Mockito.when(orderDao.findAll(Mockito.eq(pageRequest))).thenReturn(orders);
        orderService.reviewOrdersCost();
        Mockito.verify(orderDao, Mockito.atLeastOnce()).update(Mockito.any());
    }

    private static Arguments[] prepareOrder() {
        LocalDateTime localDateTime = LocalDateTime.parse("2009-10-04T15:42:20.134");
        Order order = Order.builder()
                .userId(1L)
                .cost(BigDecimal.valueOf(30.43).setScale(2, RoundingMode.HALF_UP))
                .date(localDateTime)
                .giftCertificates(prepareOrderGiftCertificates())
                .build();
        return new Arguments[]{Arguments.of(order)};
    }

    private static List<GiftCertificate> prepareOrderGiftCertificates() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        LocalDateTime firstCertificateDateTime = LocalDateTime.parse("2007-03-01T13:00:30.234");
        Set<Tag> firstTags = new HashSet<>();
        firstTags.add(new Tag(5L, "sport"));
        GiftCertificate firstCertificate = GiftCertificate.builder()
                .id(1L)
                .name("firstCertificate")
                .description("The First Certificate description")
                .price(BigDecimal.valueOf(10.20).setScale(2, RoundingMode.HALF_UP))
                .createDate(firstCertificateDateTime)
                .lastUpdateDate(firstCertificateDateTime)
                .duration(Duration.ofDays(10))
                .tags(firstTags)
                .build();

        LocalDateTime secondCertificateDateTime = LocalDateTime.parse("2010-09-02T13:00:20.354");
        Set<Tag> secondTags = new HashSet<>();
        secondTags.add(new Tag(2L, "spa"));
        secondTags.add(new Tag(3L, "holiday"));
        GiftCertificate secondCertificate = GiftCertificate.builder()
                .id(2L)
                .name("secondCertificate")
                .description("The Second Certificate description")
                .price(BigDecimal.valueOf(20.23).setScale(2, RoundingMode.HALF_UP))
                .createDate(secondCertificateDateTime)
                .lastUpdateDate(secondCertificateDateTime)
                .duration(Duration.ofDays(10))
                .tags(secondTags)
                .build();

        giftCertificates.add(firstCertificate);
        giftCertificates.add(secondCertificate);
        return giftCertificates;
    }

    private static Arguments[] prepareOrders() {
        List<Order> orders = new ArrayList<>();
        LocalDateTime firstOrderDateTime = LocalDateTime.parse("2009-10-04T15:42:20.134");
        Order firstOrder = Order.builder()
                .id(1L)
                .userId(1L)
                .cost(BigDecimal.valueOf(30.43).setScale(2, RoundingMode.HALF_UP))
                .date(firstOrderDateTime)
                .giftCertificates(prepareOrderGiftCertificates())
                .build();

        List<GiftCertificate> giftCertificates = new ArrayList<>();
        LocalDateTime firstCertificateDateTime = LocalDateTime.parse("2007-03-01T13:00:30.234");
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(5L, "sport"));
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("firstCertificate")
                .description("The First Certificate description")
                .price(BigDecimal.valueOf(10.20).setScale(2, RoundingMode.HALF_UP))
                .createDate(firstCertificateDateTime)
                .lastUpdateDate(firstCertificateDateTime)
                .duration(Duration.ofDays(10))
                .tags(tags)
                .build();
        giftCertificates.add(giftCertificate);

        LocalDateTime secondOrderDateTime = LocalDateTime.parse("2010-03-01T13:48:10.224");
        Order secondOrder = Order.builder()
                .id(2L)
                .userId(1L)
                .cost(BigDecimal.valueOf(10.20).setScale(2, RoundingMode.HALF_UP))
                .date(secondOrderDateTime)
                .giftCertificates(giftCertificates)
                .build();

        orders.add(firstOrder);
        orders.add(secondOrder);
        return new Arguments[]{Arguments.of(orders)};
    }
}