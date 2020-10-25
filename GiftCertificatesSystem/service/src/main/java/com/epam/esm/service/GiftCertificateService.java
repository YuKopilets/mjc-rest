package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.ServiceException;

import java.util.List;

public interface GiftCertificateService {
    void addGiftCertificate(String name, String description, String price, String duration);

    GiftCertificate getGiftCertificateById(Long id) throws ServiceException;

    List<GiftCertificate> getAllGiftCertificates();

    void updateGiftCertificate(Long id, String name, String description, String price, String duration) throws ServiceException;

    void removeGiftCertificate(Long id);
}
