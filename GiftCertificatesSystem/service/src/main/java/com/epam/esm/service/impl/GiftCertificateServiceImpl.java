package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.ServiceException;
import org.springframework.stereotype.Service;

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
    public void addGiftCertificate(GiftCertificate giftCertificate) {
        LocalDateTime localDateTime = LocalDateTime.now();
        giftCertificate.setCreateDate(localDateTime);
        giftCertificate.setLastUpdateDate(localDateTime);
        giftCertificateDao.save(giftCertificate);
    }

    @Override
    public GiftCertificate getGiftCertificateById(Long id) throws ServiceException {
        if (id > 0) {
            Optional<GiftCertificate> giftCertificateById = giftCertificateDao.findById(id);
            if (giftCertificateById.isPresent()) {
                return giftCertificateById.get();
            } else {
                throw new ServiceException("Tag with id=" + id + " not found");
            }
        } else {
            throw new ServiceException(id + " does not fit the allowed gap. Expected gap: 0 > id");
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
    public void updateGiftCertificate(GiftCertificate giftCertificate)
            throws ServiceException {
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
            giftCertificateDao.update(giftCertificate);
        } else {
            throw new ServiceException("Tag with id=" + giftCertificate.getId() + " not found");
        }
    }

    @Override
    public void removeGiftCertificate(Long id) throws ServiceException {
        if (id > 0) {
            giftCertificateDao.delete(id);
        } else {
            throw new ServiceException(id + " does not fit the allowed gap. Expected gap: 0 > id");
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
