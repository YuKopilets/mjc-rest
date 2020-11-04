package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.GiftCertificateQuery;

import java.util.List;

public interface GiftCertificateDao extends CrudDao<GiftCertificate> {
    void saveTags(GiftCertificate giftCertificate);

    List<GiftCertificate> findAllByQueryParams(GiftCertificateQuery giftCertificateQuery);

    void deleteTagsById(Long giftCertificateId);
}
