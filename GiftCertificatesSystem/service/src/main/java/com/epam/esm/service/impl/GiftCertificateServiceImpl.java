package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.dao.GiftCertificateQuery;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (id > 0) {
            Optional<GiftCertificate> giftCertificateById = giftCertificateDao.findById(id);
            if (giftCertificateById.isPresent()) {
                return giftCertificateById.get();
            } else {
                throw new GiftCertificateNotFoundServiceException("GiftCertificate with id=" + id + " not found");
            }
        } else {
            throw new InvalidRequestedIdServiceException(id + " does not fit the allowed gap. Expected gap: 0 > id");
        }
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificates() {
        return giftCertificateDao.findAll();
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesByQueryParams(GiftCertificateQuery giftCertificateQuery) {
        return giftCertificateDao.findAllGiftCertificatesByQueryParams(giftCertificateQuery);
    }

    @Override
    public GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate)
            throws GiftCertificateNotFoundServiceException {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(giftCertificate.getId());
        if (giftCertificateOptional.isPresent()
                && (giftCertificate.getName() != null || giftCertificate.getDescription() != null
                || giftCertificate.getPrice() != null || giftCertificate.getDuration() != null)) {
            LocalDateTime localDateTime = LocalDateTime.now();
            GiftCertificate giftCertificateById = giftCertificateOptional.get();
            if (giftCertificate.getName() != null) {
                giftCertificateById.setName(giftCertificate.getName());
            }
            if (giftCertificate.getDescription() != null) {
                giftCertificateById.setDescription(giftCertificate.getDescription());
            }
            if (giftCertificate.getPrice() != null) {
                giftCertificateById.setPrice(giftCertificate.getPrice());
            }
            if (giftCertificate.getDuration() != null) {
                giftCertificateById.setDuration(giftCertificate.getDuration());
            }
            giftCertificate.setLastUpdateDate(localDateTime);
            return giftCertificateDao.update(giftCertificateById);
        } else {
            throw new GiftCertificateNotFoundServiceException("GiftCertificate with id=" + giftCertificate.getId() +
                    " not found");
        }
    }

    @Override
    @Transactional
    public void removeGiftCertificate(Long id) throws InvalidRequestedIdServiceException {
        if (id > 0) {
            giftCertificateDao.delete(id);
            removeGiftCertificateTags(id);
        } else {
            throw new InvalidRequestedIdServiceException(id + " does not fit the allowed gap. Expected gap: 0 > id");
        }
    }

    @Override
    public void addGiftCertificateTags(GiftCertificate giftCertificate) {
        giftCertificateDao.saveGiftCertificateTags(giftCertificate);
    }

    @Override
    public void removeGiftCertificateTags(Long giftCertificateId) throws InvalidRequestedIdServiceException {
        if (giftCertificateId > 0) {
            giftCertificateDao.deleteGiftCertificateTagsByGiftCertificateId(giftCertificateId);
        } else {
            throw new InvalidRequestedIdServiceException(giftCertificateId +
                    " does not fit the allowed gap. Expected gap: 0 > id");
        }
    }
}
