package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.GiftCertificateQuery;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;

    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

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
        Optional<GiftCertificate> giftCertificateById = giftCertificateDao.findById(id);
        return giftCertificateById.orElseThrow(() ->
                new GiftCertificateNotFoundServiceException("GiftCertificate with id=" + id + " not found")
        );
    }

    @Override
    public List<GiftCertificate> getGiftCertificates(GiftCertificateQuery giftCertificateQuery) {
        if (reviewGiftCertificateQueryParams(giftCertificateQuery)) {
            return giftCertificateDao.findAllByQueryParams(giftCertificateQuery);
        } else {
            return giftCertificateDao.findAll();
        }
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
    @Transactional
    public void removeGiftCertificate(Long id) throws InvalidRequestedIdServiceException {
        validateId(id);
        giftCertificateDao.delete(id);
        removeGiftCertificateTags(id);
    }

    @Override
    public void addGiftCertificateTags(GiftCertificate giftCertificate) {
        giftCertificateDao.saveTags(giftCertificate);
    }

    @Override
    public void removeGiftCertificateTags(Long giftCertificateId) throws InvalidRequestedIdServiceException {
        validateId(giftCertificateId);
        giftCertificateDao.deleteTagsById(giftCertificateId);
    }

    private boolean reviewGiftCertificateQueryParams(GiftCertificateQuery giftCertificateQuery) {
        if (!(StringUtils.isEmpty(giftCertificateQuery.getTagName())
                && StringUtils.isEmpty(giftCertificateQuery.getPartOfName())
                && StringUtils.isEmpty(giftCertificateQuery.getPartOfDescription())
                && StringUtils.isEmpty(giftCertificateQuery.getSort()))
        ) {
            String sort = giftCertificateQuery.getSort();
            if (!StringUtils.isEmpty(sort) && !("name".equals(sort) || "date".equals(sort))) {
                giftCertificateQuery.setSort(null);
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean hasUpdateValues(GiftCertificate giftCertificate) {
        if (giftCertificate.getName() != null
                || giftCertificate.getDescription() != null
                || giftCertificate.getPrice() != null
                || giftCertificate.getDuration() != null
        ) {
            return true;
        } else {
            return false;
        }
    }

    private void setUpdateValues(GiftCertificate oldGiftCertificate, GiftCertificate giftCertificate) {
        if (giftCertificate.getName() != null) {
            oldGiftCertificate.setName(giftCertificate.getName());
        }
        if (giftCertificate.getDescription() != null) {
            oldGiftCertificate.setDescription(giftCertificate.getDescription());
        }
        if (giftCertificate.getPrice() != null) {
            oldGiftCertificate.setPrice(giftCertificate.getPrice());
        }
        if (giftCertificate.getDuration() != null) {
            oldGiftCertificate.setDuration(giftCertificate.getDuration());
        }
        oldGiftCertificate.setLastUpdateDate(LocalDateTime.now());
    }

    private void validateId(Long id) throws InvalidRequestedIdServiceException {
        if (id <= 0) {
            throw new InvalidRequestedIdServiceException("GiftCertificate id: " + id
                    + " does not fit the allowed gap. Expected gap: id > 0");
        }
    }
}
