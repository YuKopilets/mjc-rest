package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractSessionDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.GiftCertificateQuery;
import com.epam.esm.util.QueryConditionUtils;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private static final String SELECT_ALL_GIFT_CERTIFICATE_IDS = "SELECT gc.id FROM GiftCertificate gc JOIN gc.tags t";

    public GiftCertificateDaoImpl(LocalSessionFactoryBean sessionFactory) {
        super(sessionFactory);
    }

    public GiftCertificate save(GiftCertificate giftCertificate) {
        doWithSessionTransaction(session -> session.save(giftCertificate));
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
        doWithSessionTransaction(session -> session.merge(giftCertificate));
        return giftCertificate;
    }

    @Override
    public void delete(Long id) {
        doWithSessionTransaction(session -> {
            GiftCertificate giftCertificate = session.find(GiftCertificate.class, id);
            session.delete(giftCertificate);
        });
    }

    @Override
    public List<GiftCertificate> findAllByQueryParams(GiftCertificateQuery giftCertificateQuery,
                                                      PageRequest pageRequest) {
        String condition = QueryConditionUtils.generateConditionByQueryParams(giftCertificateQuery);
        String query = SELECT_ALL_GIFT_CERTIFICATE_IDS + condition;
        // This cast is correct, because the list we're creating is of the same
        // type as the one passed after operation with session. We're selecting
        // the list of gift certificate identifiers.
        @SuppressWarnings("unchecked") List<Long> certificateIds = doWithSession(session -> session.createQuery(query)
                .setFirstResult(pageRequest.calculateStartElementPosition())
                .setMaxResults(pageRequest.getPageSize())
                .setReadOnly(true)
                .list());

        return doWithSession(session -> certificateIds.stream()
                .map(id -> session.find(GiftCertificate.class, id))
                .collect(Collectors.toList())
        );
    }
}
