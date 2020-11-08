package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

/**
 * The interface Tag dao is expansion of CrudDao for additional
 * operations with <i>tag</i> table and <i>gift_Certificate_has_tag</i>
 * cross table.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see CrudDao
 */
public interface TagDao extends CrudDao<Tag> {
    /**
     * Save tag to gift certificate. Insert single record to
     * <i>gift_Certificate_has_tag</i> table.
     * Create operation (CRUD).
     *
     * @param giftCertificateId the gift certificate id
     * @param tagId             the tag id
     */
    void saveToGiftCertificate(Long giftCertificateId, Long tagId);
}
