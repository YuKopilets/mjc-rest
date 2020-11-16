package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractSessionDao;
import com.epam.esm.dao.ColumnNameConstant;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.GiftCertificateQuery;
import com.epam.esm.util.QueryConditionUtils;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The type implementation of Gift certificate dao.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see GiftCertificateDao
 * @see AbstractSessionDao
 */
@Repository
public class GiftCertificateDaoImpl extends AbstractSessionDao implements GiftCertificateDao {
    private static final String SELECT_ALL_GIFT_CERTIFICATES = "SELECT gc FROM GiftCertificate gc JOIN FETCH gc.tags t";
    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE GiftCertificate SET " +
            "name = :name, description = :description, price = :price, " +
            "lastUpdateDate = :last_update_date, duration = :duration WHERE id = :id";
    private static final String DELETE_GIFT_CERTIFICATE = "DELETE FROM GiftCertificate WHERE id = :id";
    private static final String INSERT_GIFT_CERTIFICATE_TAG = "INSERT INTO gift_certificate_has_tag " +
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
        GiftCertificate giftCertificate = doWithSession(session -> session.find(GiftCertificate.class, id));
        return Optional.ofNullable(giftCertificate);
    }

    @Override
    public List<GiftCertificate> findAll(PageRequest pageRequest) {
        return doWithSession(session -> session.createQuery(SELECT_ALL_GIFT_CERTIFICATES, GiftCertificate.class)
                .setFirstResult(pageRequest.calculateStartElementPosition())
                .setMaxResults(pageRequest.getPageSize())
                .setReadOnly(true)
                .list()
        );
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        doWithSessionTransaction(session -> session.createQuery(UPDATE_GIFT_CERTIFICATE)
                .setParameter(ColumnNameConstant.GIFT_CERTIFICATE_ID, giftCertificate.getId())
                .setParameter(ColumnNameConstant.GIFT_CERTIFICATE_NAME, giftCertificate.getName())
                .setParameter(ColumnNameConstant.GIFT_CERTIFICATE_DESCRIPTION, giftCertificate.getDescription())
                .setParameter(ColumnNameConstant.GIFT_CERTIFICATE_PRICE, giftCertificate.getPrice())
                .setParameter(ColumnNameConstant.GIFT_CERTIFICATE_LAST_UPDATE_DATE, giftCertificate.getLastUpdateDate())
                .setParameter(ColumnNameConstant.GIFT_CERTIFICATE_DURATION, giftCertificate.getDuration())
                .executeUpdate()
        );
        return giftCertificate;
    }

    @Override
    public boolean delete(Long id) {
        int updatedRows = doWithSessionTransaction(session -> session.createQuery(DELETE_GIFT_CERTIFICATE)
                .setParameter(ColumnNameConstant.GIFT_CERTIFICATE_ID, id)
                .executeUpdate()
        );
        return updatedRows > 0;
    }

    @Override
    public void saveTags(GiftCertificate giftCertificate) {
        Long giftCertificateId = giftCertificate.getId();
        Set<Tag> tags = giftCertificate.getTags();
        doWithSessionTransaction(session -> tags.stream()
                .mapToInt(tag -> session.createNativeQuery(INSERT_GIFT_CERTIFICATE_TAG)
                    .setParameter(1, giftCertificateId)
                    .setParameter(2, tag.getId())
                    .executeUpdate())
                .sum()
        );
    }

    @Override
    public List<GiftCertificate> findAllByQueryParams(GiftCertificateQuery giftCertificateQuery,
                                                      PageRequest pageRequest) {
        String condition = QueryConditionUtils.generateConditionByQueryParams(giftCertificateQuery);
        return doWithSession(session -> session.createQuery(SELECT_ALL_GIFT_CERTIFICATES + condition,
                GiftCertificate.class)
                .setFirstResult(pageRequest.calculateStartElementPosition())
                .setMaxResults(pageRequest.getPageSize())
                .setReadOnly(true)
                .list());
    }
}
