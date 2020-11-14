package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.util.GiftCertificateQuery;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateDao giftCertificateDao;
    private GiftCertificateService giftCertificateService;

    @BeforeEach
    void setUp() {
        giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao);
    }

    @AfterEach
    void tearDown() {
        giftCertificateDao = null;
        giftCertificateService = null;
    }

    @ParameterizedTest
    @MethodSource("prepareGiftCertificate")
    void addGiftCertificateTest(GiftCertificate giftCertificate) {
        giftCertificateService.addGiftCertificate(giftCertificate);
        Mockito.verify(giftCertificateDao).save(giftCertificate);
    }

    @ParameterizedTest
    @MethodSource("prepareGiftCertificate")
    void getGiftCertificateByIdTest(GiftCertificate giftCertificate) throws ServiceException {
        Optional<GiftCertificate> giftCertificateOptional = Optional.ofNullable(giftCertificate);
        Mockito.when(giftCertificateDao.findById(1L)).thenReturn(giftCertificateOptional);
        giftCertificateService.getGiftCertificateById(1L);
        Mockito.verify(giftCertificateDao, Mockito.atLeastOnce()).findById(Mockito.anyLong());
    }

    @Test
    void getGiftCertificateByIdNegativeTest() {
        Mockito.when(giftCertificateDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> giftCertificateService.getGiftCertificateById(1L));
    }

    @ParameterizedTest
    @MethodSource("prepareExpectedGiftCertificates")
    void getGiftCertificatesTest(List<GiftCertificate> exceptedGiftCertificates,
                                    GiftCertificateQuery giftCertificateQuery) {
        PageRequest pageRequest = new PageRequest(1, 10);
        Mockito.when(giftCertificateDao.findAll(Mockito.eq(pageRequest))).thenReturn(exceptedGiftCertificates);
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.getGiftCertificates(giftCertificateQuery,
                1, 10);
        assertEquals(exceptedGiftCertificates, actualGiftCertificates);
    }

    @ParameterizedTest
    @MethodSource("prepareExpectedGiftCertificates")
    void getGiftCertificatesNegativeTest(List<GiftCertificate> exceptedGiftCertificates,
                                            GiftCertificateQuery giftCertificateQuery) {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        PageRequest pageRequest = new PageRequest(1, 10);
        Mockito.when(giftCertificateDao.findAll(Mockito.eq(pageRequest))).thenReturn(giftCertificates);
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.getGiftCertificates(giftCertificateQuery,
                1, 10);
        assertNotEquals(exceptedGiftCertificates, actualGiftCertificates);
    }

    @ParameterizedTest
    @MethodSource("prepareGiftCertificate")
    void updateGiftCertificateTest(GiftCertificate giftCertificate) throws ServiceException {
        Optional<GiftCertificate> giftCertificateOptional = Optional.ofNullable(giftCertificate);
        Mockito.when(giftCertificateDao.findById(1L)).thenReturn(giftCertificateOptional);
        giftCertificateOptional.ifPresent(actualGiftCertificate -> {
            giftCertificateService.updateGiftCertificate(actualGiftCertificate);
            Mockito.verify(giftCertificateDao).update(actualGiftCertificate);
        });
    }

    @ParameterizedTest
    @MethodSource("prepareGiftCertificate")
    void updateGiftCertificateNegativeTest(GiftCertificate giftCertificate) {
        Mockito.when(giftCertificateDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class,
                () -> giftCertificateService.updateGiftCertificate(giftCertificate)
        );
    }

    @Test
    void removeGiftCertificateTest() throws ServiceException {
        Mockito.when(giftCertificateDao.delete(1L)).thenReturn(true);
        giftCertificateService.removeGiftCertificate(1L);
        Mockito.verify(giftCertificateDao).delete(Mockito.anyLong());
    }

    @Test
    void removeGiftCertificateNegativeTest() {
        assertThrows(ServiceException.class, () -> giftCertificateService.removeGiftCertificate(-1L));
    }

    private static Arguments[] prepareGiftCertificate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("test name")
                .description("test description")
                .price(BigDecimal.valueOf(20.30).setScale(2, RoundingMode.HALF_UP))
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .duration(Duration.ofDays(25))
                .build();
        return new Arguments[]{Arguments.of(giftCertificate)};
    }

    private static Arguments[] prepareExpectedGiftCertificates() {
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
        LocalDateTime firstCertificateDateTime = LocalDateTime.parse("2007-03-01T13:00:30.234");
        GiftCertificate firstCertificate = GiftCertificate.builder()
                .id(1L)
                .name("firstCertificate")
                .description("The First Certificate description")
                .price(BigDecimal.valueOf(10.20).setScale(2, RoundingMode.HALF_UP))
                .createDate(firstCertificateDateTime)
                .lastUpdateDate(firstCertificateDateTime)
                .duration(Duration.ofDays(10))
                .build();
        LocalDateTime secondCertificateDateTime = LocalDateTime.parse("2010-09-02T13:00:20.354");
        GiftCertificate secondCertificate = GiftCertificate.builder()
                .id(2L)
                .name("secondCertificate")
                .description("The Second Certificate description")
                .price(BigDecimal.valueOf(20.23).setScale(2, RoundingMode.HALF_UP))
                .createDate(secondCertificateDateTime)
                .lastUpdateDate(secondCertificateDateTime)
                .duration(Duration.ofDays(10))
                .build();
        LocalDateTime thirdCertificateDateTime = LocalDateTime.parse("2012-12-12T12:12:12.354");
        GiftCertificate thirdCertificate = GiftCertificate.builder()
                .id(3L)
                .name("thirdCertificate")
                .description("The Third Certificate description")
                .price(BigDecimal.valueOf(40.20).setScale(2, RoundingMode.HALF_UP))
                .createDate(thirdCertificateDateTime)
                .lastUpdateDate(thirdCertificateDateTime)
                .duration(Duration.ofDays(12))
                .build();
        expectedGiftCertificates.add(firstCertificate);
        expectedGiftCertificates.add(secondCertificate);
        expectedGiftCertificates.add(thirdCertificate);
        return new Arguments[]{Arguments.of(expectedGiftCertificates, prepareGiftCertificateQuery())};
    }

    private static GiftCertificateQuery prepareGiftCertificateQuery() {
        Set<String> tagNames = new HashSet<>();
        return new GiftCertificateQuery(tagNames, null, null, null, null);
    }
}