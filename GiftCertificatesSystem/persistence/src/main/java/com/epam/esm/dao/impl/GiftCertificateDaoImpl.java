package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractSessionDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.GiftCertificateQuery;
import com.epam.esm.util.QueryConditionUtils;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl extends AbstractSessionDao implements GiftCertificateDao {
    private static final String SELECT_ALL_GIFT_CERTIFICATES = "FROM GiftCertificate";
    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE GiftCertificate SET " +
            "name = :name, description = :description, price = :price, " +
            "lastUpdateDate = :last_update_date, duration = :duration WHERE id = :id";
    private static final String DELETE_GIFT_CERTIFICATE = "DELETE FROM GiftCertificate WHERE id = :id";
    private static final String INSERT_GIFT_CERTIFICATE_ID_AND_TAG_ID = "INSERT INTO gift_certificate_has_tag " +
            "(gift_certificate_id, tag_id) VALUES (?, ?)";

    public GiftCertificateDaoImpl(LocalSessionFactoryBean sessionFactory) {
        super(sessionFactory);
    }

    public GiftCertificate save(GiftCertificate giftCertificate) {
        doWithSession(session -> session.save(giftCertificate));
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(doWithSession(session -> session.find(GiftCertificate.class, id)));
    }

    @Override
    public List<GiftCertificate> findAll() {
        return doWithSession(session -> session.createQuery(SELECT_ALL_GIFT_CERTIFICATES, GiftCertificate.class)
                .setReadOnly(true)
                .list()
        );
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        doWithSessionTransaction(session -> session.createQuery(UPDATE_GIFT_CERTIFICATE)
                .setParameter("id", giftCertificate.getId())
                .setParameter("name", giftCertificate.getName())
                .setParameter("description", giftCertificate.getDescription())
                .setParameter("price", giftCertificate.getPrice())
                .setParameter("last_update_date", giftCertificate.getLastUpdateDate())
                .setParameter("duration", giftCertificate.getDuration())
                .executeUpdate()
        );
        return giftCertificate;
    }

    @Override
    public boolean delete(Long id) {
        int updatedRows = doWithSessionTransaction(session -> session.createQuery(DELETE_GIFT_CERTIFICATE)
                .setParameter("id", id)
                .executeUpdate()
        );
        return updatedRows > 0;
    }

    @Override
    public void saveTags(GiftCertificate giftCertificate) {
        Long giftCertificateId = giftCertificate.getId();
        List<Tag> tags = giftCertificate.getTags();
        doWithSessionTransaction(session -> tags.stream()
                .mapToInt(tag -> session.createNativeQuery(INSERT_GIFT_CERTIFICATE_ID_AND_TAG_ID)
                    .setParameter(1, giftCertificateId)
                    .setParameter(2, tag.getId())
                    .executeUpdate())
                .sum()
        );
    }

    @Override
    public List<GiftCertificate> findAllByQueryParams(GiftCertificateQuery giftCertificateQuery) {
        String condition = QueryConditionUtils.generateConditionByQueryParams(giftCertificateQuery);
        return doWithSession(session -> session.createQuery(SELECT_ALL_GIFT_CERTIFICATES + condition,
                GiftCertificate.class)
                .setReadOnly(true)
                .list());
    }
}
