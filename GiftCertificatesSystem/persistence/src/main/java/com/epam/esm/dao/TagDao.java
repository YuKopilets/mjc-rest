package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

public interface TagDao extends CrudDao<Tag> {
    void saveTagToGiftCertificate(Long giftCertificateId, Long tagId);

    void deleteTagFromGiftCertificatesById(Long tagId);
}
