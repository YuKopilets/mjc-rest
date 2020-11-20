package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.GiftCertificateQuery;

import java.util.List;

/**
 * The interface Gift certificate dao is expansion of CrudDao for additional
 * operations with <i>gift_Certificate</i> table and
 * <i>gift_Certificate_has_tag</i> associated table.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see CrudDao
 */
public interface GiftCertificateDao extends CrudDao<GiftCertificate> {
    /**
     * Find List of all gift certificates by query params. The {@code page
     * request} can show which part of list needed to return.
     * Read operation (CRUD).
     *
     * @param giftCertificateQuery the gift certificate query
     * @param pageRequest          the number and size of page
     * @return the list of all found gift certificates
     */
    List<GiftCertificate> findAllByQueryParams(GiftCertificateQuery giftCertificateQuery, PageRequest pageRequest);
}
