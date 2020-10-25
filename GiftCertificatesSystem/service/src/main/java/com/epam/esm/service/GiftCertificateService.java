package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.ServiceException;

import java.util.List;

public interface GiftCertificateService {
    void addGiftCertificate(String name, String description, String price, String duration);

    GiftCertificate getGiftCertificateById(String id) throws ServiceException;

    List<GiftCertificate> getAllGiftCertificates();

    List<GiftCertificate> getAllGiftCertificatesByTagName(String name);

    List<GiftCertificate> getAllGiftCertificatesByPartOfName(String name);

    List<GiftCertificate> getAllGiftCertificatesByPartOfDescription(String description);

    void updateGiftCertificate(String id, String name, String description, String price, String duration)
            throws ServiceException;

    void removeGiftCertificate(String id) throws ServiceException;

    List<GiftCertificate> sortGiftCertificatesByNameAsc();

    List<GiftCertificate> sortGiftCertificatesByNameDesc();

    List<GiftCertificate> sortGiftCertificatesByDateAsc();

    List<GiftCertificate> sortGiftCertificatesByDateDesc();
}
