package com.epam.esm.service.impl;

import com.epam.esm.persistence.repository.GiftCertificateFilterRepository;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.TagNotFoundServiceException;
import com.epam.esm.persistence.util.GiftCertificateQuery;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type implementation of Gift certificate service.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see GiftCertificateService
 */
@Service
@Validated
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final String NAME_SORT = "name";
    private static final String DATE_SORT = "date";

    private final GiftCertificateRepository giftCertificateRepository;
    private final GiftCertificateFilterRepository giftCertificateFilterRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public GiftCertificate addGiftCertificate(@Valid GiftCertificate giftCertificate)
            throws TagNotFoundServiceException {
        LocalDateTime localDateTime = LocalDateTime.now();
        giftCertificate.setCreateDate(localDateTime);
        giftCertificate.setLastUpdateDate(localDateTime);
        initGiftCertificateByTags(giftCertificate);
        giftCertificateRepository.save(giftCertificate);
        return giftCertificate;
    }

    @Override
    public GiftCertificate getGiftCertificateById(Long id) throws GiftCertificateNotFoundServiceException {
        return giftCertificateRepository.findById(id)
                .orElseThrow(() -> new GiftCertificateNotFoundServiceException(id));
    }

    @Override
    public Page<GiftCertificate> getGiftCertificates(GiftCertificateQuery giftCertificateQuery,
                                                     Pageable pageable) {
        if (reviewGiftCertificateQueryParams(giftCertificateQuery)) {
            return giftCertificateFilterRepository.findAllByQueryParams(giftCertificateQuery, pageable);
        }
        return giftCertificateRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public GiftCertificate updateGiftCertificate(@Valid GiftCertificate giftCertificate)
            throws GiftCertificateNotFoundServiceException {
        GiftCertificate oldGiftCertificateById = giftCertificateRepository.findById(giftCertificate.getId())
                .orElseThrow(() -> new GiftCertificateNotFoundServiceException(giftCertificate.getId()));

        if (hasUpdateValues(giftCertificate)) {
            setUpdateValues(oldGiftCertificateById, giftCertificate);
            giftCertificateRepository.save(oldGiftCertificateById);
        }
        return oldGiftCertificateById;
    }

    @Override
    public void removeGiftCertificate(Long id) throws GiftCertificateNotFoundServiceException {
        giftCertificateRepository.findById(id).orElseThrow(() -> new GiftCertificateNotFoundServiceException(id));
        giftCertificateRepository.deleteById(id);
    }

    private void initGiftCertificateByTags(GiftCertificate giftCertificate) throws TagNotFoundServiceException {
        Set<Tag> tags = giftCertificate.getTags().stream()
                .map(this::findGiftCertificateTag)
                .collect(Collectors.toSet());
        giftCertificate.setTags(tags);
    }

    private Tag findGiftCertificateTag(Tag tag) throws TagNotFoundServiceException {
        return tagRepository.findById(tag.getId()).orElseThrow(() -> new TagNotFoundServiceException(tag.getId()));
    }

    private boolean reviewGiftCertificateQueryParams(GiftCertificateQuery giftCertificateQuery) {
        if (isValidQueryParams(giftCertificateQuery)) {
            initSortParam(giftCertificateQuery);
            return true;
        }
        return false;
    }

    private boolean hasUpdateValues(GiftCertificate giftCertificate) {
        String name = giftCertificate.getName();
        String description = giftCertificate.getDescription();
        BigDecimal price = giftCertificate.getPrice();
        Duration duration = giftCertificate.getDuration();
        return Stream.of(name, description, price, duration).anyMatch(Objects::nonNull);
    }

    private void setUpdateValues(GiftCertificate oldGiftCertificate, GiftCertificate giftCertificate) {
        Optional.ofNullable(giftCertificate.getName()).ifPresent(oldGiftCertificate::setName);
        Optional.ofNullable(giftCertificate.getDescription()).ifPresent(oldGiftCertificate::setDescription);
        Optional.ofNullable(giftCertificate.getPrice()).ifPresent(oldGiftCertificate::setPrice);
        Optional.ofNullable(giftCertificate.getDuration()).ifPresent(oldGiftCertificate::setDuration);
        oldGiftCertificate.setLastUpdateDate(LocalDateTime.now());
    }

    private boolean isValidQueryParams(GiftCertificateQuery giftCertificateQuery) {
        Set<String> tagNames = giftCertificateQuery.getTagNames();
        String partOfName = giftCertificateQuery.getPartOfName();
        String partOfDescription = giftCertificateQuery.getPartOfDescription();
        String sort = giftCertificateQuery.getSort();
        return !tagNames.isEmpty() || Stream.of(partOfName, partOfDescription, sort).anyMatch(StringUtils::isNotEmpty);
    }

    private void initSortParam(GiftCertificateQuery giftCertificateQuery) {
        String sort = giftCertificateQuery.getSort();
        if (isNotValidSortParam(sort)) {
            giftCertificateQuery.setSort(StringUtils.EMPTY);
        }
    }

    private boolean isNotValidSortParam(String sort) {
        return StringUtils.isNotEmpty(sort) && !(NAME_SORT.equals(sort) || DATE_SORT.equals(sort));
    }
}
