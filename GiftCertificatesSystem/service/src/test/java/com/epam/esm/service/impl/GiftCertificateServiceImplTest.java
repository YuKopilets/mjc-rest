package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.exception.ServiceException;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateDao giftCertificateDao;
    @Mock
    private TagDao tagDao;
    private GiftCertificateService giftCertificateService;

    @BeforeEach
    void setUp() {
        giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
        tagDao = Mockito.mock(TagDaoImpl.class);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao, tagDao);
    }

    @AfterEach
    void tearDown() {
        giftCertificateDao = null;
        tagDao = null;
        giftCertificateService = null;
    }

    @ParameterizedTest
    @MethodSource("prepareGiftCertificate")
    void addGiftCertificateTest(GiftCertificate giftCertificate) {
        Optional<Tag> tagOptional = Optional.ofNullable(buildTag(5L, "sport"));
        Mockito.when(tagDao.findById(5L)).thenReturn(tagOptional);
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
        assertThrows(GiftCertificateNotFoundServiceException.class,
                () -> giftCertificateService.getGiftCertificateById(1L));
    }

    @ParameterizedTest
    @MethodSource("prepareExpectedGiftCertificates")
    void getGiftCertificatesTest(List<GiftCertificate> exceptedGiftCertificates,
                                    GiftCertificateQuery giftCertificateQuery) {
        PageRequest pageRequest = new PageRequest(1, 10);
        Mockito.when(giftCertificateDao.findAll(Mockito.eq(pageRequest))).thenReturn(exceptedGiftCertificates);
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.getGiftCertificates(giftCertificateQuery,
                pageRequest);
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
                pageRequest);
        assertNotEquals(exceptedGiftCertificates, actualGiftCertificates);
    }

    @ParameterizedTest
    @MethodSource("prepareExpectedGiftCertificates")
    void getGiftCertificatesByQueryParamsTest(List<GiftCertificate> exceptedGiftCertificates,
                                 GiftCertificateQuery giftCertificateQuery) {
        PageRequest pageRequest = new PageRequest(1, 10);
        Set<String> tagNames = giftCertificateQuery.getTagNames();
        tagNames.add("test tag");

        Mockito.when(giftCertificateDao.findAll(Mockito.eq(pageRequest))).thenReturn(exceptedGiftCertificates);
        giftCertificateService.getGiftCertificates(giftCertificateQuery, pageRequest);
        Mockito.verify(giftCertificateDao, Mockito.atLeastOnce())
                .findAllByQueryParams(giftCertificateQuery, pageRequest);
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
        assertThrows(GiftCertificateNotFoundServiceException.class,
                () -> giftCertificateService.updateGiftCertificate(giftCertificate)
        );
    }

    @ParameterizedTest
    @MethodSource("prepareGiftCertificate")
    void removeGiftCertificateTest(GiftCertificate giftCertificate) throws ServiceException {
        Optional<GiftCertificate> giftCertificateOptional = Optional.of(giftCertificate);
        Mockito.when(giftCertificateDao.findById(1L)).thenReturn(giftCertificateOptional);
        giftCertificateService.removeGiftCertificate(1L);
        Mockito.verify(giftCertificateDao).delete(Mockito.anyLong());
    }

    @Test
    void removeGiftCertificateNegativeTest() {
        assertThrows(ServiceException.class, () -> giftCertificateService.removeGiftCertificate(-1L));
    }

    private static Arguments[] prepareGiftCertificate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Set<Tag> tags = new HashSet<>();
        tags.add(buildTag(5L, "sport"));
        GiftCertificate giftCertificate = buildGiftCertificate(1L, "test name", "test description",
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
        return new Arguments[]{Arguments.of(expectedGiftCertificates, prepareGiftCertificateQuery())};
    }

    private static GiftCertificateQuery prepareGiftCertificateQuery() {
        Set<String> tagNames = new HashSet<>();
        return new GiftCertificateQuery(tagNames, null, null, null, null);
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