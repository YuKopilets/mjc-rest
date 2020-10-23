package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface GiftCertificateDao extends CrudDao<GiftCertificate> {
    void saveGiftCertificateIdAndTagId(Long giftCertificateId, Long tagId);

    List<Tag> findAllTagsByGiftCertificateId(Long id);
}
