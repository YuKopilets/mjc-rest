package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.GiftCertificateQuery;

import java.util.List;

/**
 * The interface Gift certificate dao is expansion of CrudDao for additional
 * operations with <i>gift_Certificate<i/> table and
 * <i>gift_Certificate_has_tag<i/> cross table.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see CrudDao
 */
public interface GiftCertificateDao extends CrudDao<GiftCertificate> {
    /**
     * Save tags to <i>gift_Certificate_has_tag<i/> table.
     * Create operation (CRUD).
     *
     * @param giftCertificate the gift certificate
     */
    void saveTags(GiftCertificate giftCertificate);

    /**
     * Find List of all gift certificates by query params.
     * Read operation (CRUD).
     *
     * @param giftCertificateQuery the gift certificate query
     * @return the list of all found gift certificates
     */
    List<GiftCertificate> findAllByQueryParams(GiftCertificateQuery giftCertificateQuery);

    /**
     * Delete tags by id from <i>gift_Certificate_has_tag<i/> table.
     * Delete operation (CRUD).
     *
     * @param giftCertificateId the gift certificate id
     */
    void deleteTagsById(Long giftCertificateId);
}
