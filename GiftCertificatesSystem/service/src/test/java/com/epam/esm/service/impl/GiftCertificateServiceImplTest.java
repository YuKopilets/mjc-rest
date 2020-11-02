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
import java.util.List;
import java.util.Optional;

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
    void addGiftCertificateTest() {
        GiftCertificate giftCertificate = createGiftCertificate();
        giftCertificateService.addGiftCertificate(giftCertificate);
        Mockito.verify(giftCertificateDao).save(giftCertificate);
    }

    @Test
    void getGiftCertificateByIdTest() throws ServiceException {
        Optional<GiftCertificate> giftCertificate = Optional.ofNullable(createGiftCertificate());
        Mockito.when(giftCertificateDao.findById(1L)).thenReturn(giftCertificate);
        giftCertificateService.getGiftCertificateById(1L);
        Mockito.verify(giftCertificateDao).findById(Mockito.anyLong());
    }

    @Test
    void getGiftCertificateByIdNegativeTest() {
        Mockito.when(giftCertificateDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> giftCertificateService.getGiftCertificateById(1L));
    }

    @Test
    void getAllGiftCertificatesTest() {
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
    void updateGiftCertificateTest() throws ServiceException {
        Optional<GiftCertificate> giftCertificate = Optional.ofNullable(createGiftCertificate());
        Mockito.when(giftCertificateDao.findById(1L)).thenReturn(giftCertificate);
        giftCertificateService.updateGiftCertificate(giftCertificate.get());
        Mockito.verify(giftCertificateDao).update(giftCertificate.get());
    }

    @Test
    void updateGiftCertificateNegativeTest() {
        Mockito.when(giftCertificateDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class,
                () -> giftCertificateService.updateGiftCertificate(createGiftCertificate())
        );
    }

    @Test
    void removeGiftCertificateTest() throws ServiceException {
        giftCertificateService.removeGiftCertificate(1L);
        Mockito.verify(giftCertificateDao).delete(Mockito.anyLong());
    }

    @Test
    void removeGiftCertificateNegativeTest() {
        Mockito.when(giftCertificateDao.delete(-1L)).thenReturn(true);
        assertThrows(ServiceException.class, () -> giftCertificateService.removeGiftCertificate(-1L));
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