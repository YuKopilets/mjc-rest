package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.*;
import com.epam.esm.util.GiftCertificateQuery;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * The type implementation of Gift certificate service.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see GiftCertificateService
 */
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final String NAME_SORT = "name";
    private static final String DATE_SORT = "date";

    private final GiftCertificateDao giftCertificateDao;

    @Override
    @Transactional
    public GiftCertificate addGiftCertificate(GiftCertificate giftCertificate) {
        LocalDateTime localDateTime = LocalDateTime.now();
        giftCertificate.setCreateDate(localDateTime);
        giftCertificate.setLastUpdateDate(localDateTime);
        giftCertificateDao.save(giftCertificate);
        addGiftCertificateTags(giftCertificate);
        return giftCertificate;
    }

    @Override
    public GiftCertificate getGiftCertificateById(Long id)
            throws GiftCertificateNotFoundServiceException, InvalidRequestedIdServiceException {
        validateId(id);
        return giftCertificateDao.findById(id).orElseThrow(
                () -> new GiftCertificateNotFoundServiceException("GiftCertificate with id=" + id + " not found")
        );
    }

    @Override
    public List<GiftCertificate> getGiftCertificates(GiftCertificateQuery giftCertificateQuery, int page, int pageSize)
            throws PageNumberNotValidServiceException {
        validatePageNumber(page);
        validatePageSize(pageSize);
        PageRequest pageRequest = new PageRequest(page, pageSize);
        if (reviewGiftCertificateQueryParams(giftCertificateQuery)) {
            return giftCertificateDao.findAllByQueryParams(giftCertificateQuery, pageRequest);
        }
        return giftCertificateDao.findAll(pageRequest);
    }

    @Override
    public GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate)
            throws GiftCertificateNotFoundServiceException {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(giftCertificate.getId());
        if (giftCertificateOptional.isPresent() && hasUpdateValues(giftCertificate)) {
            GiftCertificate oldGiftCertificateById = giftCertificateOptional.get();
            setUpdateValues(oldGiftCertificateById, giftCertificate);
            return giftCertificateDao.update(oldGiftCertificateById);
        } else {
            throw new GiftCertificateNotFoundServiceException("GiftCertificate with id=" + giftCertificate.getId()
                    + " not found");
        }
    }

    @Override
    public void removeGiftCertificate(Long id)
            throws InvalidRequestedIdServiceException, DeleteByRequestedIdServiceException {
        if (!giftCertificateDao.delete(id)) {
            throw new DeleteByRequestedIdServiceException("Delete gift certificate by requested id: " + id
                    + " not completed");
        }
    }

    private void addGiftCertificateTags(GiftCertificate giftCertificate) {
        giftCertificateDao.saveTags(giftCertificate);
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

    private void validateId(Long id) throws InvalidRequestedIdServiceException {
        if (id <= 0) {
            throw new InvalidRequestedIdServiceException("GiftCertificate id: " + id
                    + " does not fit the allowed gap. Expected gap: id > 0");
        }
    }

    private void validatePageNumber(int page) {
        if (page < 0) {
            throw new PageNumberNotValidServiceException("Gift certificates can't be load. " + page
                    + " is not valid value. Page must be positive number");
        }
    }

    private void validatePageSize(int size) {
        if (size < 0) {
            throw new PageSizeNotValidServiceException("Gift certificates can't be load. " + size
                    + " is not valid value. Page must be positive number");
        }
    }
}
