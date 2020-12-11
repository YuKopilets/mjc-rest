package com.epam.esm.service;

import com.epam.esm.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.exception.TagNotFoundServiceException;
import com.epam.esm.util.GiftCertificateQuery;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

/**
 * The interface Gift certificate service.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
public interface GiftCertificateService {
    /**
     * Add new gift certificate.
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate
     * @throws TagNotFoundServiceException in case of {@code tag with
     *                                     current id not found}
     */
    GiftCertificate addGiftCertificate(@Valid GiftCertificate giftCertificate) throws TagNotFoundServiceException;

    /**
     * Get single gift certificate by id.
     *
     * @param id the gift certificate id
     * @return the gift certificate found by id
     * @throws GiftCertificateNotFoundServiceException in case of {@code gift
     * certificate with current id not found}
     */
    GiftCertificate getGiftCertificateById(Long id) throws GiftCertificateNotFoundServiceException;

    /**
     * Get list of gift certificates. If query params for finding certificates
     * are empty, method will return list of all gift certificates
     *
     * @param giftCertificateQuery the query params for finding list of
     * gift certificate
     * @param pageable             the pageable
     * @return the list of gift certificates matching search parameters
     */
    Page<GiftCertificate> getGiftCertificates(GiftCertificateQuery giftCertificateQuery, Pageable pageable);

    /**
     * Update gift certificate. Update values of gift certificate if there are
     * any changes after comparing with old gift certificate values
     *
     * @param giftCertificate the gift certificate
     * @return gift certificate
     * @throws GiftCertificateNotFoundServiceException in case of {@code gift
     * certificate with current id not found}
     */
    GiftCertificate updateGiftCertificate(@Valid GiftCertificate giftCertificate)
            throws GiftCertificateNotFoundServiceException;

    /**
     * Remove gift certificate by id.
     *
     * @param id the gift certificate id
     * @throws GiftCertificateNotFoundServiceException in case of {@code gift
     * certificate with current id not found}
     */
    void removeGiftCertificate(Long id) throws GiftCertificateNotFoundServiceException;
}
