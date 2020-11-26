package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.GiftCertificateQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The interface Gift certificate filter repository is expansion of CrudDao for additional
 * operations with <i>gift_Certificate</i> table and
 * <i>gift_Certificate_has_tag</i> associated table.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
public interface GiftCertificateFilterRepository {
    /**
     * Find List of all gift certificates by query params. The {@code page
     * request} can show which part of list needed to return.
     * Read operation (CRUD).
     *
     * @param giftCertificateQuery the gift certificate query
     * @return the list of all found gift certificates
     */
    Page<GiftCertificate> findAllByQueryParams(GiftCertificateQuery giftCertificateQuery, Pageable pageable);
}
