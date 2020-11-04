package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

public interface TagDao extends CrudDao<Tag> {
    void saveToGiftCertificate(Long giftCertificateId, Long tagId);

    void deleteFromGiftCertificatesById(Long tagId);
}
