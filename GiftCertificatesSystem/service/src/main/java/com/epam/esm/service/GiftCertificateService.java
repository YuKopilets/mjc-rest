package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.ServiceException;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificate addGiftCertificate(GiftCertificate giftCertificate);

    GiftCertificate getGiftCertificateById(Long id) throws ServiceException;

    List<GiftCertificate> getAllGiftCertificates();

    List<GiftCertificate> getAllGiftCertificatesByTagName(String name);

    List<GiftCertificate> getAllGiftCertificatesByPartOfName(String name);

    List<GiftCertificate> getAllGiftCertificatesByPartOfDescription(String description);

    GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate)
            throws ServiceException;

    void removeGiftCertificate(Long id) throws ServiceException;

    List<GiftCertificate> sortGiftCertificatesByNameAsc();

    List<GiftCertificate> sortGiftCertificatesByNameDesc();

    List<GiftCertificate> sortGiftCertificatesByDateAsc();

    List<GiftCertificate> sortGiftCertificatesByDateDesc();
}
