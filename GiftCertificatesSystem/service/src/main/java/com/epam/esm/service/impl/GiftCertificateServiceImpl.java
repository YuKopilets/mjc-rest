package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.ServiceException;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public void addGiftCertificate(String name, String description, String price, String duration) {
        LocalDateTime localDateTime = LocalDateTime.now();
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name(name)
                .description(description)
                .price(new BigDecimal(price).setScale(2, RoundingMode.HALF_UP))
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .duration(new Integer(duration))
                .build();
        giftCertificateDao.save(giftCertificate);
    }

    @Override
    public GiftCertificate getGiftCertificateById(String id) throws ServiceException {
        if (NumberUtils.isCreatable(id)) {
            Long numId = NumberUtils.createLong(id);
            if (numId > 0 && id.length() < 21) {
                Optional<GiftCertificate> giftCertificateById = giftCertificateDao.findById(numId);
                if (giftCertificateById.isPresent()) {
                    return giftCertificateById.get();
                } else {
                    throw new ServiceException("Tag with id=" + id + " not found");
                }
            } else {
                throw new ServiceException(numId + " does not fit the allowed gap. Expected gap: 0 > id");
            }
        } else {
            throw new ServiceException(id + " isn't a number. Expected type: Long");
        }
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificates() {
        return giftCertificateDao.findAll();
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificatesByTagName(String name) {
        return giftCertificateDao.findAllGiftCertificatesByTagName(name);
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificatesByPartOfName(String name) {
        return giftCertificateDao.findAllGiftCertificatesByPartOfName(name);
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificatesByPartOfDescription(String description) {
        return giftCertificateDao.findAllGiftCertificatesByPartOfDescription(description);
    }

    @Override
    public void updateGiftCertificate(String id, String name, String description, String price, String duration)
            throws ServiceException {
        if (NumberUtils.isCreatable(id)) {
            Long numId = NumberUtils.createLong(id);
            if (numId > 0 && id.length() < 21) {
                Optional<GiftCertificate> giftCertificateById = giftCertificateDao.findById(numId);
                if (giftCertificateById.isPresent()
                        && (name != null || description != null || price != null || duration != null)) {
                    LocalDateTime localDateTime = LocalDateTime.now();
                    GiftCertificate giftCertificate = giftCertificateById.get();
                    if (name != null) {
                        giftCertificate.setName(name);
                    }
                    if (description != null) {
                        giftCertificate.setDescription(description);
                    }
                    if (price != null) {
                        giftCertificate.setPrice(new BigDecimal(price).setScale(2, RoundingMode.HALF_UP));
                    }
                    if (duration != null) {
                        giftCertificate.setDuration(new Integer(duration));
                    }
                    giftCertificate.setLastUpdateDate(localDateTime);
                    giftCertificateDao.update(giftCertificate);
                } else {
                    throw new ServiceException("Tag with id=" + id + " not found");
                }
            } else {
                throw new ServiceException(numId + " does not fit the allowed gap. Expected gap: 0 > id");
            }
        } else {
            throw new ServiceException(id + " isn't a number. Expected type: Long");
        }
    }

    @Override
    public void removeGiftCertificate(String id) throws ServiceException {
        if (NumberUtils.isCreatable(id)) {
            Long numId = NumberUtils.createLong(id);
            if (numId > 0 && id.length() < 21) {
                giftCertificateDao.delete(numId);
            } else {
                throw new ServiceException(numId + " does not fit the allowed gap. Expected gap: 0 > id");
            }
        } else {
            throw new ServiceException(id + " isn't a number. Expected type: Long");
        }
    }

    @Override
    public List<GiftCertificate> sortGiftCertificatesByNameAsc() {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll();
        giftCertificates.sort((g1, g2) -> g1.getName().compareTo(g2.getName()));
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> sortGiftCertificatesByNameDesc() {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll();
        giftCertificates.sort((g1, g2) -> g2.getName().compareTo(g1.getName()));
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> sortGiftCertificatesByDateAsc() {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll();
        giftCertificates.sort((g1, g2) -> g1.getCreateDate().compareTo(g2.getCreateDate()));
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> sortGiftCertificatesByDateDesc() {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll();
        giftCertificates.sort((g1, g2) -> g2.getCreateDate().compareTo(g1.getCreateDate()));
        return giftCertificates;
    }
}
