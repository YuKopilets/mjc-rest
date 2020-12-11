package com.epam.esm.dao.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.GiftCertificateFilterRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.util.GiftCertificateQuery;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
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
    private GiftCertificateRepository giftCertificateRepository;
    @Autowired
    private GiftCertificateFilterRepository giftCertificateFilterRepository;

    @Test
    void findGiftCertificateByIdTest() {
        Optional<GiftCertificate> giftCertificateById = giftCertificateRepository.findById(1L);
        String expected = "firstCertificate";
        String actual = giftCertificateById.map(GiftCertificate::getName).orElse(StringUtils.EMPTY);
        assertEquals(expected, actual);
    }

    @Test
    void findGiftCertificateByIdNegativeTest() {
        Optional<GiftCertificate> giftCertificateById = giftCertificateRepository.findById(4L);
        assertFalse(giftCertificateById.isPresent());
    }

    @ParameterizedTest
    @MethodSource("prepareExpectedGiftCertificates")
    void findAllGiftCertificatesTest(List<GiftCertificate> expectedGiftCertificates) {
        PageRequest pageRequest = PageRequest.of(0, 3);
        List<GiftCertificate> actualGiftCertificates = giftCertificateRepository.findAll(pageRequest).getContent();
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @ParameterizedTest
    @MethodSource("prepareExpectedGiftCertificates")
    void findAllGiftCertificatesNegativeTest(List<GiftCertificate> expectedGiftCertificates) {
        PageRequest pageRequest = PageRequest.of(0, 2);
        List<GiftCertificate> actualGiftCertificates = giftCertificateRepository.findAll(pageRequest).getContent();
        assertNotEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @ParameterizedTest
    @MethodSource("prepareGiftCertificateQueryAndExpectedList")
    void findAllGiftCertificatesByTagNamesTest(GiftCertificateQuery giftCertificateQuery, List<GiftCertificate> expected) {
        PageRequest pageRequest = PageRequest.of(0, 3);
        List<GiftCertificate> actual = giftCertificateFilterRepository.findAllByQueryParams(giftCertificateQuery,
                pageRequest).getContent();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("prepareExpectedGiftCertificates")
    void findAllGiftCertificatesByQueryParamsTest(List<GiftCertificate> expected) {
        GiftCertificateQuery giftCertificateQuery = prepareGiftCertificateQuery();
        PageRequest pageRequest = PageRequest.of(0, 3);
        List<GiftCertificate> actual = giftCertificateFilterRepository.findAllByQueryParams(giftCertificateQuery,
                pageRequest).getContent();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("prepareGiftCertificate")
    void updateTest(GiftCertificate expectedGiftCertificate) {
        expectedGiftCertificate.setId(1L);
        giftCertificateRepository.save(expectedGiftCertificate);
        Optional<GiftCertificate> giftCertificateById = giftCertificateRepository.findById(1L);
        GiftCertificate actualGiftCertificate = giftCertificateById.orElseGet(GiftCertificate::new);
        assertEquals(expectedGiftCertificate, actualGiftCertificate);
    }

    @Test
    void deleteTest() {
        giftCertificateRepository.deleteById(1L);
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(1L);
        assertFalse(giftCertificate.isPresent());
    }

    private static Arguments[] prepareGiftCertificate() {
        LocalDateTime localDateTime = LocalDateTime.parse("2007-03-01T13:00:30.234");
        Set<Tag> tags = new HashSet<>();
        tags.add(buildTag(5L, "sport"));
        GiftCertificate giftCertificate = buildGiftCertificate(null, "test name", "test description",
                20.30, localDateTime, localDateTime, 25, tags);
        return new Arguments[]{Arguments.of(giftCertificate)};
    }

    private static Arguments[] prepareExpectedGiftCertificates() {
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
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

        LocalDateTime thirdCertificateDateTime = LocalDateTime.parse("2012-12-12T12:12:12.354");
        Set<Tag> thirdTags = new HashSet<>();
        thirdTags.add(buildTag(1L, "rest"));
        thirdTags.add(buildTag(6L, "tourism"));
        GiftCertificate thirdCertificate = buildGiftCertificate(3L, "thirdCertificate",
                "The Third Certificate description", 40.20, thirdCertificateDateTime,
                thirdCertificateDateTime, 12, thirdTags);

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