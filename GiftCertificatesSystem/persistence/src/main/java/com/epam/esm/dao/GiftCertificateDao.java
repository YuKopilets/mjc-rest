package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.GiftCertificateQuery;

import java.util.List;

public interface GiftCertificateDao extends CrudDao<GiftCertificate> {
    void saveGiftCertificateTags(GiftCertificate giftCertificate);

    List<GiftCertificate> findAllGiftCertificatesByQueryParams(GiftCertificateQuery giftCertificateQuery);

    void deleteTagsFromGiftCertificateById(Long giftCertificateId);
}
