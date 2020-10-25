package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao extends CrudDao<GiftCertificate> {
    List<GiftCertificate> findAllGiftCertificatesByTagName(String name);

    List<GiftCertificate> findAllGiftCertificatesByPartOfName(String name);

    List<GiftCertificate> findAllGiftCertificatesByPartOfDescription(String description);
}
