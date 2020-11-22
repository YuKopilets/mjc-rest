package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.UserNotFoundServiceException;
import com.epam.esm.service.OrderService;
import com.epam.esm.exception.ServiceException;
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
    @Mock
    private UserDao userDao;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderDao = Mockito.mock(OrderDaoImpl.class);
        giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
        userDao = Mockito.mock(UserDaoImpl.class);
        orderService = new OrderServiceImpl(orderDao, userDao, giftCertificateDao);
    }

    @AfterEach
    void tearDown() {
        orderDao = null;
        giftCertificateDao = null;
        orderService = null;
        userDao = null;
    }

    @ParameterizedTest
    @MethodSource("prepareOrder")
    void addOrderTest(Order order, User user) {
        Optional<User> userOptional = Optional.of(user);
        Mockito.when(userDao.findById(1L)).thenReturn(userOptional);

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
    void addOrderNegativeTest(Order order, User user) {
        assertThrows(UserNotFoundServiceException.class, ()-> orderService.addOrder(order));
    }

    @ParameterizedTest
    @MethodSource("prepareOrders")
    void getUserOrdersTest(List<Order> exceptedOrders, User user) {
        Optional<User> userOptional = Optional.of(user);
        Mockito.when(userDao.findByLogin("login")).thenReturn(userOptional);

        PageRequest pageRequest = new PageRequest(1, 10);
        Mockito.when(orderDao.findOrdersByUserLogin(Mockito.eq("login"), Mockito.eq(pageRequest)))
                .thenReturn(exceptedOrders);

        List<Order> actualOrders = orderService.getUserOrders("login", pageRequest);
        assertEquals(exceptedOrders, actualOrders);
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

    private static Arguments[] prepareOrder() {
        LocalDateTime localDateTime = LocalDateTime.parse("2009-10-04T15:42:20.134");
        Order order = Order.builder()
                .userId(1L)
                .cost(BigDecimal.valueOf(30.43).setScale(2, RoundingMode.HALF_UP))
                .date(localDateTime)
                .giftCertificates(prepareOrderGiftCertificates())
                .build();
        User user = User.builder()
                .id(1L)
                .login("user")
                .build();
        return new Arguments[]{Arguments.of(order, user)};
    }

    private static List<GiftCertificate> prepareOrderGiftCertificates() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        LocalDateTime firstCertificateDateTime = LocalDateTime.parse("2007-03-01T13:00:30.234");
        Set<Tag> firstTags = new HashSet<>();
        firstTags.add(buildTag(5L, "sport"));
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
        secondTags.add(buildTag(2L, "spa"));
        secondTags.add(buildTag(3L, "holiday"));
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
        tags.add(buildTag(5L, "sport"));
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

        User user = User.builder()
                .id(1L)
                .login("user")
                .build();
        return new Arguments[]{Arguments.of(orders, user)};
    }

    private static Tag buildTag(Long id, String name) {
        return Tag.builder()
                .id(id)
                .name(name)
                .build();
    }
}