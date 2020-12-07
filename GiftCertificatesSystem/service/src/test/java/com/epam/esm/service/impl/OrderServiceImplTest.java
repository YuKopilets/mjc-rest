package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
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
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
    private OrderRepository orderRepository;
    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Mock
    private UserRepository userRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderServiceImpl(orderRepository, userRepository, giftCertificateRepository);
    }

    @AfterEach
    void tearDown() {
        orderRepository = null;
        giftCertificateRepository = null;
        userRepository = null;
        orderService = null;
    }

    @ParameterizedTest
    @MethodSource("prepareOrders")
    void getUserOrdersTest(List<Order> exceptedOrders, User user) {
        Optional<User> userOptional = Optional.of(user);
        Mockito.when(userRepository.findById(1L)).thenReturn(userOptional);

        PageRequest pageRequest = PageRequest.of(1, 10);
        Mockito.when(orderRepository.findOrdersByUserId(1L, pageRequest))
                .thenReturn(new PageImpl<>(exceptedOrders));

        List<Order> actualOrders = orderService.getUserOrders(1L, pageRequest).getContent();
        assertEquals(exceptedOrders, actualOrders);
    }

    @ParameterizedTest
    @MethodSource("prepareOrder")
    void getUserOrderByIdTest(Order order) {
        Optional<Order> orderOptional = Optional.ofNullable(order);
        Mockito.when(orderRepository.findById(1L)).thenReturn(orderOptional);
        orderService.getUserOrderById(1L);
        Mockito.verify(orderRepository, Mockito.atLeastOnce()).findById(Mockito.anyLong());
    }

    @Test
    void getUserOrderByIdNegativeTest() {
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> orderService.getUserOrderById(1L));
    }

    private static Arguments[] prepareOrder() {
        LocalDateTime localDateTime = LocalDateTime.parse("2009-10-04T15:42:20.134");
        Order order = buildOrder(1L, 1L, 30.43, localDateTime, prepareOrderGiftCertificates());
        User user = User.builder()
                .id(1L)
                .build();
        return new Arguments[]{Arguments.of(order, user)};
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

        User user = User.builder()
                .id(1L)
                .build();
        return new Arguments[]{Arguments.of(orders, user)};
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