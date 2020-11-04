package com.epam.esm.dao.impl;

import com.epam.esm.context.TestConfig;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
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
@ActiveProfiles("test")
@Sql({"/drop_gift_certificates_system_schema.sql", "/create_gift_certificates_system_schema.sql"})
@Sql(scripts = "/gift_certificates_system_inserts.sql")
class GiftCertificateDaoImplTest {
    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @ParameterizedTest
    @MethodSource("prepareGiftCertificate")
    void saveTest(GiftCertificate giftCertificate) {
        giftCertificateDao.save(giftCertificate);
        Long expected = 4L;
        Long actual = giftCertificate.getId();
        assertEquals(expected, actual);
    }

    @Test
    void findGiftCertificateByIdTest() {
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

    @ParameterizedTest
    @MethodSource("prepareExpectedGiftCertificates")
    void findAllGiftCertificatesTest(List<GiftCertificate> expectedGiftCertificates) {
        List<GiftCertificate> actualGiftCertificates = giftCertificateDao.findAll();
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void findAllGiftCertificatesNegativeTest() {
        List<GiftCertificate> actualGiftCertificates = giftCertificateDao.findAll();
        boolean isEmpty = actualGiftCertificates.isEmpty();
        assertFalse(isEmpty);
    }

    @ParameterizedTest
    @MethodSource("prepareGiftCertificate")
    void updateTest(GiftCertificate expectedGiftCertificate) {
        expectedGiftCertificate.setId(1L);
        giftCertificateDao.update(expectedGiftCertificate);
        GiftCertificate actualGiftCertificate = giftCertificateDao.findById(1L).get();
        assertEquals(expectedGiftCertificate, actualGiftCertificate);
    }

    @Test
    void deleteTest() {
        boolean isDeleted = giftCertificateDao.delete(1L);
        assertTrue(isDeleted);
    }

    @Test
    void deleteNegativeTest() {
        boolean isDeleted = giftCertificateDao.delete(4L);
        assertFalse(isDeleted);
    }

    private static Arguments[] prepareGiftCertificate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(5L, "sport"));
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name("test name")
                .description("test description")
                .price(BigDecimal.valueOf(20.30).setScale(2, RoundingMode.HALF_UP))
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .duration(25)
                .tags(tags)
                .build();
        return new Arguments[]{Arguments.of(giftCertificate)};
    }

    private static Arguments[] prepareExpectedGiftCertificates() {
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
        LocalDateTime firstCertificateDateTime = LocalDateTime.parse("2007-03-01T13:00:30.234");
        List<Tag> firstTags = new ArrayList<>();
        firstTags.add(new Tag(5L, "sport"));
        GiftCertificate firstCertificate = GiftCertificate.builder()
                .id(1L)
                .name("firstCertificate")
                .description("The First Certificate description")
                .price(BigDecimal.valueOf(10.20).setScale(2, RoundingMode.HALF_UP))
                .createDate(firstCertificateDateTime)
                .lastUpdateDate(firstCertificateDateTime)
                .duration(10)
                .tags(firstTags)
                .build();
        LocalDateTime secondCertificateDateTime = LocalDateTime.parse("2010-09-02T13:00:20.354");
        List<Tag> secondTags = new ArrayList<>();
        secondTags.add(new Tag(2L, "spa"));
        secondTags.add(new Tag(3L, "holiday"));
        GiftCertificate secondCertificate = GiftCertificate.builder()
                .id(2L)
                .name("secondCertificate")
                .description("The Second Certificate description")
                .price(BigDecimal.valueOf(20.23).setScale(2, RoundingMode.HALF_UP))
                .createDate(secondCertificateDateTime)
                .lastUpdateDate(secondCertificateDateTime)
                .duration(10)
                .tags(secondTags)
                .build();
        LocalDateTime thirdCertificateDateTime = LocalDateTime.parse("2012-12-12T12:12:12.354");
        List<Tag> thirdTags = new ArrayList<>();
        thirdTags.add(new Tag(1L, "rest"));
        thirdTags.add(new Tag(6L, "tourism"));
        GiftCertificate thirdCertificate = GiftCertificate.builder()
                .id(3L)
                .name("thirdCertificate")
                .description("The Third Certificate description")
                .price(BigDecimal.valueOf(40.20).setScale(2, RoundingMode.HALF_UP))
                .createDate(thirdCertificateDateTime)
                .lastUpdateDate(thirdCertificateDateTime)
                .duration(12)
                .tags(thirdTags)
                .build();
        expectedGiftCertificates.add(firstCertificate);
        expectedGiftCertificates.add(secondCertificate);
        expectedGiftCertificates.add(thirdCertificate);
        return new Arguments[]{Arguments.of(expectedGiftCertificates)};
    }
}