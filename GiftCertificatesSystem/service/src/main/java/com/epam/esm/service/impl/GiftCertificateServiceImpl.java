package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.ServiceException;
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
    public GiftCertificate getGiftCertificateById(Long id) throws ServiceException {
        Optional<GiftCertificate> giftCertificateById = giftCertificateDao.findById(id);
        if (giftCertificateById.isPresent()) {
            return giftCertificateById.get();
        } else {
            throw new ServiceException("Tag with id=" + id + " not found!");
        }
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificates() {
        return giftCertificateDao.findAll();
    }

    @Override
    public void updateGiftCertificate(Long id, String name, String description, String price, String duration) throws ServiceException {
        LocalDateTime localDateTime = LocalDateTime.now();
        Optional<GiftCertificate> giftCertificateById = giftCertificateDao.findById(id);
        if (giftCertificateById.isPresent()) {
            GiftCertificate giftCertificate = giftCertificateById.get();
            if (name != null || description != null || price != null || duration != null) {
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
            }
        } else {
            throw new ServiceException("Tag with id=" + id + " not found!");
        }
    }

    @Override
    public void removeGiftCertificate(Long id) {
        giftCertificateDao.delete(id);
    }
}
