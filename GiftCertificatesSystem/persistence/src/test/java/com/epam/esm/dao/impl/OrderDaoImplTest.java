package com.epam.esm.dao.impl;

import com.epam.esm.context.TestConfig;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

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

@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
@Sql({"/drop_gift_certificates_system_schema.sql", "/create_gift_certificates_system_schema.sql"})
@Sql(scripts = {"/gift_certificates_system_inserts.sql"})
class OrderDaoImplTest {
    @Autowired
    private OrderDao orderDao;

    @ParameterizedTest
    @MethodSource("prepareOrder")
    void saveTest(Order order) {
        orderDao.save(order);
        Long expected = 3L;
        Long actual = order.getId();
        assertEquals(expected, actual);
    }

    @Test
    void findByIdTest() {
        Optional<Order> orderById = orderDao.findById(1L);
        Long expected = 1L;
        Long actual = orderById.map(Order::getUserId).orElse(0L);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdNegativeTest() {
        Optional<Order> orderById = orderDao.findById(3L);
        assertFalse(orderById.isPresent());
    }

    @ParameterizedTest
    @MethodSource("prepareOrders")
    void findAllTest(List<Order> expectedOrders) {
        PageRequest pageRequest = new PageRequest(1, 2);
        List<Order> actualOrders = orderDao.findAll(pageRequest);
        assertEquals(expectedOrders, actualOrders);
    }

    @ParameterizedTest
    @MethodSource("prepareOrders")
    void findAllNegativeTest(List<Order> expectedOrders) {
        PageRequest pageRequest = new PageRequest(1, 1);
        List<Order> actualOrders = orderDao.findAll(pageRequest);
        assertNotEquals(expectedOrders, actualOrders);
    }

    @ParameterizedTest
    @MethodSource("prepareOrder")
    void updateTest(Order order) {
        order.setId(2L);
        orderDao.update(order);
        Optional<Order> orderById = orderDao.findById(2L);
        BigDecimal expected = order.getCost();
        BigDecimal actual = orderById.map(Order::getCost).orElse(BigDecimal.valueOf(0));
        assertEquals(expected, actual);
    }

    @Test
    void deleteTest() {
        boolean isDeleted = orderDao.delete(1L);
        assertTrue(isDeleted);
    }

    @Test
    void deleteNegativeTest() {
        boolean isDeleted = orderDao.delete(3L);
        assertFalse(isDeleted);
    }

    @Test
    void countOrdersTest() {
        long expected = 2;
        long actual = orderDao.countOrders();
        assertEquals(expected, actual);
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