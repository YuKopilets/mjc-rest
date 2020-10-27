package com.epam.esm.dao.impl;

import com.epam.esm.context.TestConfig;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
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
@Sql(scripts = {"classpath:create_gift_certificate_table.sql"})
class GiftCertificateDaoImplTest {
    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Test
    void savePositiveTest() {
        GiftCertificate certificate = createGiftCertificate();
        giftCertificateDao.save(certificate);
        Long expected = 4L;
        Long actual = certificate.getId();
        assertEquals(expected, actual);
    }

    @Test
    void saveNegativeTest() {
        GiftCertificate certificate = createGiftCertificate();
        giftCertificateDao.save(certificate);
        Long expected = 2L;
        Long actual = certificate.getId();
        assertNotEquals(expected, actual);
    }

    @Test
    void findGiftCertificateByIdPositiveTest() {
        Optional<GiftCertificate> giftCertificateById = giftCertificateDao.findById(1L);
        String expected = "firstCertificate";
        String actual = giftCertificateById.get().getName();
        assertEquals(expected, actual);
    }

    @Test
    void findGiftCertificateByIdNegativeTest() {
        Optional<GiftCertificate> giftCertificateById = giftCertificateDao.findById(4L);
        assertFalse(giftCertificateById.isPresent());
    }

    @Test
    void findAllGiftCertificatesPositiveTest() {
        List<GiftCertificate> expectedGiftCertificates = createExpectedGiftCertificates();
        List<GiftCertificate> actualGiftCertificates = giftCertificateDao.findAll();
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void findAllGiftCertificatesNegativeTest() {
        List<GiftCertificate> actualGiftCertificates = giftCertificateDao.findAll();
        boolean isEmpty = actualGiftCertificates.isEmpty();
        assertFalse(isEmpty);
    }

    @Test
    void updatePositiveTest() {
        GiftCertificate expectedCertificate = createGiftCertificate();
        expectedCertificate.setId(1L);
        giftCertificateDao.update(expectedCertificate);
        GiftCertificate actualGiftCertificate = giftCertificateDao.findById(1L).get();
        assertEquals(expectedCertificate, actualGiftCertificate);
    }

    @Test
    void updateNegativeTest() {
        GiftCertificate expectedCertificate = createGiftCertificate();
        expectedCertificate.setId(1L);
        giftCertificateDao.update(expectedCertificate);
        expectedCertificate.setName("other name");
        GiftCertificate actualGiftCertificate = giftCertificateDao.findById(1L).get();
        assertNotEquals(expectedCertificate, actualGiftCertificate);
    }

    @Test
    void deletePositiveTest() {
        boolean isDeleted = giftCertificateDao.delete(1L);
        assertTrue(isDeleted);
    }

    @Test
    void deleteNegativeTest() {
        boolean isDeleted = giftCertificateDao.delete(4L);
        assertFalse(isDeleted);
    }

    private GiftCertificate createGiftCertificate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return GiftCertificate.builder()
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