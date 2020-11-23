package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractSessionDao;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The type implementation of Tag dao.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see TagDao
 * @see AbstractSessionDao
 */
@Repository
public class TagDaoImpl extends AbstractSessionDao implements TagDao {
    private static final String SELECT_ALL_TAGS = "SELECT t FROM Tag t";
    private static final String INSERT_GIFT_CERTIFICATE_TAG = "INSERT INTO gift_certificate_has_tag " +
            "(gift_certificate_id, tag_id) VALUES (?, ?)";
    private static final String SELECT_MOST_WIDELY_USED_TAG = "SELECT t.id, t.name FROM user_order o " +
            "INNER JOIN order_has_gift_certificate ogc ON o.id = ogc.order_id " +
            "AND o.user_account_id = (SELECT user_account_id FROM user_order o " +
            "GROUP BY o.user_account_id " +
            "            ORDER BY sum(cost) DESC " +
            "            LIMIT 1) " +
            "INNER JOIN gift_certificate gc ON ogc.gift_certificate_id = gc.id " +
            "INNER JOIN gift_certificate_has_tag gct ON gc.id = gct.gift_certificate_id " +
            "INNER JOIN tag t ON gct.tag_id = t.id " +
            "GROUP BY t.id " +
            "    ORDER BY count(t.id) DESC " +
            "LIMIT 1";
    private static final String DELETE_GIFT_CERTIFICATES_TAG = "DELETE FROM gift_certificate_has_tag WHERE tag_id = ?";

    public TagDaoImpl(LocalSessionFactoryBean sessionFactory) {
        super(sessionFactory);
    }

    public Tag save(Tag tag) {
        doWithSessionTransaction(session -> session.save(tag));
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        Tag tag = doWithSession(session -> session.find(Tag.class, id));
        return Optional.ofNullable(tag);
    }

    @Override
    public List<Tag> findAll(PageRequest pageRequest) {
        return doWithSession(session -> session.createQuery(SELECT_ALL_TAGS, Tag.class)
                .setFirstResult(pageRequest.calculateStartElementPosition())
                .setMaxResults(pageRequest.getPageSize())
                .setReadOnly(true)
                .list()
        );
    }

    @Override
    public Tag update(Tag tag) {
        return tag;
    }

    @Override
    public void delete(Long id) {
        doWithSessionTransaction(session -> {
            Tag tag = session.find(Tag.class, id);
            session.delete(tag);
        });
    }

    @Override
    public Tag findMostWidelyUsedTag() {
        return doWithSession(session -> session.createNativeQuery(SELECT_MOST_WIDELY_USED_TAG, Tag.class)
                .setReadOnly(true)
                .getSingleResult()
        );
    }

    @Override
    public void saveToGiftCertificate(Long giftCertificateId, Long tagId) {
        doWithSessionTransaction(session -> session.createSQLQuery(INSERT_GIFT_CERTIFICATE_TAG)
                .setParameter(1, giftCertificateId)
                .setParameter(2, tagId)
                .executeUpdate());
    }

    @Override
    public void deleteGiftCertificatesTag(Long id) {
        doWithSessionTransaction(session -> session.createSQLQuery(DELETE_GIFT_CERTIFICATES_TAG)
                .setParameter(1, id)
                .executeUpdate()
        );
    }
}
