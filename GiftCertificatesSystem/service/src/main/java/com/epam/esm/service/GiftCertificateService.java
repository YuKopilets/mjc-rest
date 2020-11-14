package com.epam.esm.service;

import com.epam.esm.service.exception.DeleteByRequestedIdServiceException;
import com.epam.esm.service.exception.PageNumberNotValidServiceException;
import com.epam.esm.util.GiftCertificateQuery;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;

import java.util.List;

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
     */
    GiftCertificate addGiftCertificate(GiftCertificate giftCertificate);

    /**
     * Get single gift certificate by id.
     *
     * @param id the gift certificate id
     * @return the gift certificate found by id
     * @throws GiftCertificateNotFoundServiceException in case of {@code gift
     * certificate with current id not found}
     * @throws InvalidRequestedIdServiceException      in case of {@code id is
     * not valid to do operation}
     */
    GiftCertificate getGiftCertificateById(Long id)
            throws GiftCertificateNotFoundServiceException, InvalidRequestedIdServiceException;

    /**
     * Get list of gift certificates. If query params for finding certificates
     * are empty, method will return list of all gift certificates
     *
     * @param giftCertificateQuery the query params for finding list of
     * gift certificate
     * @param page                 the page number
     * @param pageSize             the page size
     * @return the list of gift certificates matching search parameters
     * @throws PageNumberNotValidServiceException in case of {@code page
     *                                         number is not valid to get list}
     */
    List<GiftCertificate> getGiftCertificates(GiftCertificateQuery giftCertificateQuery, int page, int pageSize)
            throws PageNumberNotValidServiceException;

    /**
     * Update gift certificate. Update values of gift certificate if there are
     * any changes after comparing with old gift certificate values
     *
     * @param giftCertificate the gift certificate
     * @return gift certificate
     * @throws GiftCertificateNotFoundServiceException in case of {@code gift
     * certificate with current id not found}
     */
    GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate)
            throws GiftCertificateNotFoundServiceException;

    /**
     * Remove gift certificate by id.
     *
     * @param id the gift certificate id
     * @throws InvalidRequestedIdServiceException  in case of {@code id is not
     * valid to do operation}
     * @throws DeleteByRequestedIdServiceException in case of {@code certificate
     * by current id hasn't been deleted}
     */
    void removeGiftCertificate(Long id) throws InvalidRequestedIdServiceException, DeleteByRequestedIdServiceException;
}
