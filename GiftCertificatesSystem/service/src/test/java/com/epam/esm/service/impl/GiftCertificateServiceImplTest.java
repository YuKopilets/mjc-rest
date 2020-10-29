package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.context.TestConfig;
import com.epam.esm.service.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class GiftCertificateServiceImplTest {
    @Autowired
    private GiftCertificateDao giftCertificateDao;
    @Autowired
    private GiftCertificateService giftCertificateService;

    @Test
    void getGiftCertificateByIdPositiveTest() {
        Optional<GiftCertificate> giftCertificate = Optional.ofNullable(createGiftCertificate());
        Mockito.when(giftCertificateDao.findById(1L)).thenReturn(giftCertificate);
        assertDoesNotThrow(() -> giftCertificateService.getGiftCertificateById(1L));
    }

    @Test
    void getGiftCertificateByIdNegativeTest() {
        Mockito.when(giftCertificateDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> giftCertificateService.getGiftCertificateById(1L));
    }

    @Test
    void getAllGiftCertificatesPositiveTest() {
        List<GiftCertificate> exceptedGiftCertificates = createExpectedGiftCertificates();
        Mockito.when(giftCertificateDao.findAll()).thenReturn(exceptedGiftCertificates);
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.getAllGiftCertificates();
        assertEquals(exceptedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void getAllGiftCertificatesNegativeTest() {
        List<GiftCertificate> exceptedGiftCertificates = createExpectedGiftCertificates();
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        Mockito.when(giftCertificateDao.findAll()).thenReturn(giftCertificates);
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.getAllGiftCertificates();
        assertNotEquals(exceptedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void updateGiftCertificatePositiveTest() {
        Optional<GiftCertificate> giftCertificate = Optional.ofNullable(createGiftCertificate());
        Mockito.when(giftCertificateDao.findById(1L)).thenReturn(giftCertificate);
        assertDoesNotThrow(() -> giftCertificateService.updateGiftCertificate(giftCertificate.get()));
        Mockito.when(giftCertificateDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> giftCertificateService.getGiftCertificateById(1L));
    }

    @Test
    void updateGiftCertificateNegativeTest() {
        Mockito.when(giftCertificateDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class,
                () -> giftCertificateService.updateGiftCertificate(createGiftCertificate())
        );
    }

    @Test
    void removeGiftCertificatePositiveTest() {
        Mockito.when(giftCertificateDao.delete(1L)).thenReturn(true);
        assertDoesNotThrow(() -> giftCertificateService.removeGiftCertificate(1L));
    }

    @Test
    void removeGiftCertificateNegativeTest() {
        Mockito.when(giftCertificateDao.delete(-1L)).thenReturn(true);
        assertThrows(ServiceException.class, () -> giftCertificateService.removeGiftCertificate(-1L));
    }

    @Test
    void sortGiftCertificatesByNameAscPositiveTest() {
        List<GiftCertificate> giftCertificates = createExpectedGiftCertificates();
        Mockito.when(giftCertificateDao.findAll()).thenReturn(giftCertificates);
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>(giftCertificates);
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.sortGiftCertificatesByNameAsc();
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void sortGiftCertificatesByNameAscNegativeTest() {
        List<GiftCertificate> expectedGiftCertificates = createExpectedGiftCertificates();
        Mockito.when(giftCertificateDao.findAll()).thenReturn(expectedGiftCertificates);
        expectedGiftCertificates = expectedGiftCertificates.stream()
                .sorted(Comparator.comparingInt(o -> o.getName().length()))
                .collect(Collectors.toList());
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.sortGiftCertificatesByNameAsc();
        assertNotEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void sortGiftCertificatesByNameDescPositiveTest() {
        List<GiftCertificate> giftCertificates = createExpectedGiftCertificates();
        Mockito.when(giftCertificateDao.findAll()).thenReturn(giftCertificates);
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
        expectedGiftCertificates.add(giftCertificates.get(2));
        expectedGiftCertificates.add(giftCertificates.get(1));
        expectedGiftCertificates.add(giftCertificates.get(0));
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.sortGiftCertificatesByNameDesc();
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void sortGiftCertificatesByNameDescNegativeTest() {
        List<GiftCertificate> giftCertificates = createExpectedGiftCertificates();
        Mockito.when(giftCertificateDao.findAll()).thenReturn(giftCertificates);
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>(giftCertificates);
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.sortGiftCertificatesByNameDesc();
        assertNotEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void sortGiftCertificatesByDateAscPositiveTest() {
        List<GiftCertificate> giftCertificates = createExpectedGiftCertificates();
        Mockito.when(giftCertificateDao.findAll()).thenReturn(giftCertificates);
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>(giftCertificates);
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.sortGiftCertificatesByDateAsc();
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void sortGiftCertificatesByDateAscNegativeTest() {
        List<GiftCertificate> giftCertificates = createExpectedGiftCertificates();
        Mockito.when(giftCertificateDao.findAll()).thenReturn(giftCertificates);
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.sortGiftCertificatesByDateAsc();
        assertNotEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void sortGiftCertificatesByDateDescPositiveTest() {
        List<GiftCertificate> giftCertificates = createExpectedGiftCertificates();
        Mockito.when(giftCertificateDao.findAll()).thenReturn(giftCertificates);
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
        expectedGiftCertificates.add(giftCertificates.get(2));
        expectedGiftCertificates.add(giftCertificates.get(1));
        expectedGiftCertificates.add(giftCertificates.get(0));
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.sortGiftCertificatesByDateDesc();
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void sortGiftCertificatesByDateDescNegativeTest() {
        List<GiftCertificate> giftCertificates = createExpectedGiftCertificates();
        Mockito.when(giftCertificateDao.findAll()).thenReturn(giftCertificates);
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>(giftCertificates);
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.sortGiftCertificatesByDateDesc();
        assertNotEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    private GiftCertificate createGiftCertificate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return GiftCertificate.builder()
                .id(1L)
                .name("test name")
                .description("test description")
                .price(BigDecimal.valueOf(20.30).setScale(2, RoundingMode.HALF_UP))
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .duration(25)
                .build();
    }

    private List<GiftCertificate> createExpectedGiftCertificates() {
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
        LocalDateTime firstCertificateDateTime = LocalDateTime.parse("2007-03-01T13:00:30.234");
        LocalDateTime secondCertificateDateTime = LocalDateTime.parse("2010-09-02T13:00:20.354");
        LocalDateTime thirdCertificateDateTime = LocalDateTime.parse("2012-12-12T12:12:12.354");
        GiftCertificate firstCertificate = GiftCertificate.builder()
                .id(1L)
                .name("firstCertificate")
                .description("The First Certificate description")
                .price(BigDecimal.valueOf(10.20).setScale(2, RoundingMode.HALF_UP))
                .createDate(firstCertificateDateTime)
                .lastUpdateDate(firstCertificateDateTime)
                .duration(10)
                .build();
        GiftCertificate secondCertificate = GiftCertificate.builder()
                .id(2L)
                .name("secondCertificate")
                .description("The Second Certificate description")
                .price(BigDecimal.valueOf(20.23).setScale(2, RoundingMode.HALF_UP))
                .createDate(secondCertificateDateTime)
                .lastUpdateDate(secondCertificateDateTime)
                .duration(10)
                .build();
        GiftCertificate thirdCertificate = GiftCertificate.builder()
                .id(3L)
                .name("thirdCertificate")
                .description("The Third Certificate description")
                .price(BigDecimal.valueOf(40.20).setScale(2, RoundingMode.HALF_UP))
                .createDate(thirdCertificateDateTime)
                .lastUpdateDate(thirdCertificateDateTime)
                .duration(12)
                .build();
        expectedGiftCertificates.add(firstCertificate);
        expectedGiftCertificates.add(secondCertificate);
        expectedGiftCertificates.add(thirdCertificate);
        return expectedGiftCertificates;
    }
}