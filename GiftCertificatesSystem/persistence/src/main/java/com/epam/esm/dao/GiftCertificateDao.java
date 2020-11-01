package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao extends CrudDao<GiftCertificate> {
    void saveGiftCertificateTags(GiftCertificate giftCertificate);

    List<GiftCertificate> findAllGiftCertificatesByTagName(String name);

    List<GiftCertificate> findAllGiftCertificatesByPartOfName(String name);

    List<GiftCertificate> findAllGiftCertificatesByPartOfDescription(String description);

    void deleteGiftCertificateTagsByGiftCertificateId(Long giftCertificateId);
}
