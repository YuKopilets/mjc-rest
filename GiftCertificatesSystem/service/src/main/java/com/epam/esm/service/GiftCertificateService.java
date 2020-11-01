package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import com.epam.esm.service.query.GiftCertificateQuery;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificate addGiftCertificate(GiftCertificate giftCertificate);

    GiftCertificate getGiftCertificateById(Long id)
            throws GiftCertificateNotFoundServiceException, InvalidRequestedIdServiceException;

    List<GiftCertificate> getAllGiftCertificates();

    List<GiftCertificate> getGiftCertificatesByQueryParams(GiftCertificateQuery giftCertificateQuery);

    List<GiftCertificate> getAllGiftCertificatesByTagName(String name);

    List<GiftCertificate> getAllGiftCertificatesByPartOfName(String partOfName);

    List<GiftCertificate> getAllGiftCertificatesByPartOfDescription(String partOfDescription);

    GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate)
            throws GiftCertificateNotFoundServiceException;

    void removeGiftCertificate(Long id) throws InvalidRequestedIdServiceException;

    void addGiftCertificateTags(GiftCertificate giftCertificate);

    List<GiftCertificate> sortGiftCertificatesByNameAsc();

    List<GiftCertificate> sortGiftCertificatesByNameDesc();

    List<GiftCertificate> sortGiftCertificatesByDateAsc();

    List<GiftCertificate> sortGiftCertificatesByDateDesc();

    void removeGiftCertificateTags(Long giftCertificateId) throws InvalidRequestedIdServiceException;
}
