package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao extends Dao {
    void save(GiftCertificate giftCertificate);

    Optional<GiftCertificate> findGiftCertificateById(Long id);

    List<GiftCertificate> getAllGiftCertificates();

    void update(GiftCertificate giftCertificate);

    void delete(Long id);
}
