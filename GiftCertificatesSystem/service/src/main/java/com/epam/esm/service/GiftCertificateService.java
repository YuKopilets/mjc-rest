package com.epam.esm.service;

import com.epam.esm.util.GiftCertificateQuery;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificate addGiftCertificate(GiftCertificate giftCertificate);

    GiftCertificate getGiftCertificateById(Long id)
            throws GiftCertificateNotFoundServiceException, InvalidRequestedIdServiceException;

    List<GiftCertificate> getGiftCertificates(GiftCertificateQuery giftCertificateQuery);

    /**
     *
     * @param giftCertificate
     * @return
     * @throws GiftCertificateNotFoundServiceException in case of {@code id}
     */
    GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate)
            throws GiftCertificateNotFoundServiceException;

    void removeGiftCertificate(Long id) throws InvalidRequestedIdServiceException;

    void addGiftCertificateTags(GiftCertificate giftCertificate);

    void removeGiftCertificateTags(Long giftCertificateId) throws InvalidRequestedIdServiceException;
}
