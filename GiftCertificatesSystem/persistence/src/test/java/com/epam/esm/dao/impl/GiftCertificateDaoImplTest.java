package com.epam.esm.dao.impl;

import com.epam.esm.context.TestConfig;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.GiftCertificateQuery;
import org.apache.commons.lang3.StringUtils;
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
        String actual = giftCertificateById.map(GiftCertificate::getName).orElse(StringUtils.EMPTY);
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
        PageRequest pageRequest = new PageRequest(1, 3);
        List<GiftCertificate> actualGiftCertificates = giftCertificateDao.findAll(pageRequest);
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @ParameterizedTest
    @MethodSource("prepareExpectedGiftCertificates")
    void findAllGiftCertificatesNegativeTest(List<GiftCertificate> expectedGiftCertificates) {
        PageRequest pageRequest = new PageRequest(1, 2);
        List<GiftCertificate> actualGiftCertificates = giftCertificateDao.findAll(pageRequest);
        assertNotEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @ParameterizedTest
    @MethodSource("prepareGiftCertificateQueryAndExpectedList")
    void findAllGiftCertificatesByTagNamesTest(GiftCertificateQuery giftCertificateQuery, List<GiftCertificate> expected) {
        PageRequest pageRequest = new PageRequest(1, 3);
        List<GiftCertificate> actual = giftCertificateDao.findAllByQueryParams(giftCertificateQuery, pageRequest);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("prepareExpectedGiftCertificates")
    void findAllGiftCertificatesByQueryParamsTest(List<GiftCertificate> expected) {
        GiftCertificateQuery giftCertificateQuery = prepareGiftCertificateQuery();
        PageRequest pageRequest = new PageRequest(1, 3);
        List<GiftCertificate> actual = giftCertificateDao.findAllByQueryParams(giftCertificateQuery, pageRequest);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("prepareGiftCertificate")
    void updateTest(GiftCertificate expectedGiftCertificate) {
        expectedGiftCertificate.setId(1L);
        giftCertificateDao.update(expectedGiftCertificate);
        Optional<GiftCertificate> giftCertificateById = giftCertificateDao.findById(1L);
        GiftCertificate actualGiftCertificate = giftCertificateById.orElseGet(GiftCertificate::new);
        assertEquals(expectedGiftCertificate, actualGiftCertificate);
    }

    @Test
    void deleteTest() {
        giftCertificateDao.delete(1L);
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.findById(1L);
        assertFalse(giftCertificate.isPresent());
    }

    private static Arguments[] prepareGiftCertificate() {
        LocalDateTime localDateTime = LocalDateTime.parse("2007-03-01T13:00:30.234");
        Set<Tag> tags = new HashSet<>();
        tags.add(buildTag(5L, "sport"));
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name("test name")
                .description("test description")
                .price(BigDecimal.valueOf(20.30).setScale(2, RoundingMode.HALF_UP))
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .duration(Duration.ofDays(25))
                .tags(tags)
                .build();
        return new Arguments[]{Arguments.of(giftCertificate)};
    }

    private static Arguments[] prepareExpectedGiftCertificates() {
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
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

        LocalDateTime thirdCertificateDateTime = LocalDateTime.parse("2012-12-12T12:12:12.354");
        Set<Tag> thirdTags = new HashSet<>();
        thirdTags.add(buildTag(1L, "rest"));
        thirdTags.add(buildTag(6L, "tourism"));
        GiftCertificate thirdCertificate = GiftCertificate.builder()
                .id(3L)
                .name("thirdCertificate")
                .description("The Third Certificate description")
                .price(BigDecimal.valueOf(40.20).setScale(2, RoundingMode.HALF_UP))
                .createDate(thirdCertificateDateTime)
                .lastUpdateDate(thirdCertificateDateTime)
                .duration(Duration.ofDays(12))
                .tags(thirdTags)
                .build();

        expectedGiftCertificates.add(firstCertificate);
        expectedGiftCertificates.add(secondCertificate);
        expectedGiftCertificates.add(thirdCertificate);
        return new Arguments[]{Arguments.of(expectedGiftCertificates)};
    }

    private static Arguments[] prepareGiftCertificateQueryAndExpectedList() {
        Set<String> tags = new HashSet<>();
        tags.add("sport");
        tags.add("spa");
        GiftCertificateQuery query = new GiftCertificateQuery(tags, null, null, null, null);

        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
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

        expectedGiftCertificates.add(firstCertificate);
        expectedGiftCertificates.add(secondCertificate);
        return new Arguments[]{Arguments.of(query, expectedGiftCertificates)};
    }

    private GiftCertificateQuery prepareGiftCertificateQuery() {
        Set<String> tagNames = new HashSet<>();
        tagNames.add("sport");
        tagNames.add("spa");
        tagNames.add("rest");
        return new GiftCertificateQuery(tagNames, null, "Certificate", null, null);
    }

    private static Tag buildTag(Long id, String name) {
        return Tag.builder()
                .id(id)
                .name(name)
                .build();
    }
}