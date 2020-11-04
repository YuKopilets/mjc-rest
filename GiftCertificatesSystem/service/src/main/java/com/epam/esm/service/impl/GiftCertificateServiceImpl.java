package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.DeleteByRequestedIdServiceException;
import com.epam.esm.util.GiftCertificateQuery;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
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
    public void removeGiftCertificate(Long id)
            throws InvalidRequestedIdServiceException, DeleteByRequestedIdServiceException {
        if (giftCertificateDao.delete(id)) {
            removeGiftCertificateTags(id);
        } else {
            throw new DeleteByRequestedIdServiceException("Delete gift certificate by requested id: " + id
                    + " not completed");
        }
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
        if (StringUtils.isNotEmpty(giftCertificateQuery.getTagName())
                || StringUtils.isNotEmpty(giftCertificateQuery.getPartOfName())
                || StringUtils.isNotEmpty(giftCertificateQuery.getPartOfDescription())
                || StringUtils.isNotEmpty(giftCertificateQuery.getSort())
        ) {
            String sort = giftCertificateQuery.getSort();
            if (StringUtils.isNotEmpty(sort) && !("name".equals(sort) || "date".equals(sort))) {
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
