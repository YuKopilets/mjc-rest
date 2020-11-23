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

    @Test
    void deleteTest() {
        orderDao.delete(1L);
        Optional<Order> order = orderDao.findById(1L);
        assertFalse(order.isPresent());
    }

    private static Arguments[] prepareOrder() {
        LocalDateTime localDateTime = LocalDateTime.parse("2009-10-04T15:42:20.134");
        Order order = buildOrder(1L, 1L, 30.43, localDateTime, prepareOrderGiftCertificates());
        return new Arguments[]{Arguments.of(order)};
    }

    private static List<GiftCertificate> prepareOrderGiftCertificates() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        LocalDateTime firstCertificateDateTime = LocalDateTime.parse("2007-03-01T13:00:30.234");
        Set<Tag> firstTags = new HashSet<>();
        firstTags.add(buildTag(5L, "sport"));
        GiftCertificate firstCertificate = buildGiftCertificate(1L, "firstCertificate",
                "The First Certificate description", 10.20, firstCertificateDateTime,
                firstCertificateDateTime, 10, firstTags);

        LocalDateTime secondCertificateDateTime = LocalDateTime.parse("2010-09-02T13:00:20.354");
        Set<Tag> secondTags = new HashSet<>();
        secondTags.add(buildTag(2L, "spa"));
        secondTags.add(buildTag(3L, "holiday"));
        GiftCertificate secondCertificate = buildGiftCertificate(2L, "secondCertificate",
                "The Second Certificate description", 20.23, secondCertificateDateTime,
                secondCertificateDateTime, 10, secondTags);

        giftCertificates.add(firstCertificate);
        giftCertificates.add(secondCertificate);
        return giftCertificates;
    }

    private static Arguments[] prepareOrders() {
        List<Order> orders = new ArrayList<>();
        LocalDateTime firstOrderDateTime = LocalDateTime.parse("2009-10-04T15:42:20.134");
        Order firstOrder = buildOrder(1L, 1L, 30.43, firstOrderDateTime, prepareOrderGiftCertificates());

        List<GiftCertificate> giftCertificates = new ArrayList<>();
        LocalDateTime firstCertificateDateTime = LocalDateTime.parse("2007-03-01T13:00:30.234");
        Set<Tag> tags = new HashSet<>();
        tags.add(buildTag(5L, "sport"));
        GiftCertificate giftCertificate = buildGiftCertificate(1L, "firstCertificate",
                "The First Certificate description", 10.20, firstCertificateDateTime,
                firstCertificateDateTime, 10, tags);
        giftCertificates.add(giftCertificate);

        LocalDateTime secondOrderDateTime = LocalDateTime.parse("2010-03-01T13:48:10.224");
        Order secondOrder = buildOrder(2L, 1L, 10.20, secondOrderDateTime, giftCertificates);

        orders.add(firstOrder);
        orders.add(secondOrder);
        return new Arguments[]{Arguments.of(orders)};
    }

    private static Tag buildTag(Long id, String name) {
        return Tag.builder()
                .id(id)
                .name(name)
                .build();
    }

    private static Order buildOrder(Long id, Long userId, Double cost, LocalDateTime dateTime,
                                    List<GiftCertificate> giftCertificates) {
        return Order.builder()
                .id(id)
                .userId(userId)
                .cost(BigDecimal.valueOf(cost).setScale(2, RoundingMode.HALF_UP))
                .date(dateTime)
                .giftCertificates(giftCertificates)
                .build();
    }

    private static GiftCertificate buildGiftCertificate(Long id, String name, String description, Double price,
                                                        LocalDateTime createDate, LocalDateTime lastUpdateDate,
                                                        Integer duration, Set<Tag> tags) {
        return GiftCertificate.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP))
                .createDate(createDate)
                .lastUpdateDate(lastUpdateDate)
                .duration(Duration.ofDays(duration))
                .tags(tags)
                .build();
    }
}