package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractSessionDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl extends AbstractSessionDao implements TagDao {
    private static final String SELECT_ALL_TAGS = "FROM Tag";
    private static final String DELETE_TAG = "DELETE FROM Tag WHERE id = :id";
    private static final String INSERT_GIFT_CERTIFICATE_TAG = "INSERT INTO gift_certificate_has_tag " +
            "(gift_certificate_id, tag_id) VALUES (?, ?)";

    public TagDaoImpl(LocalSessionFactoryBean sessionFactory) {
        super(sessionFactory);
    }

    public Tag save(Tag tag) {
        doWithSession(session -> session.save(tag));
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(doWithSession(session -> session.find(Tag.class, id)));
    }

    @Override
    public List<Tag> findAll() {
        return doWithSession(session -> session.createQuery(SELECT_ALL_TAGS, Tag.class)
                .setReadOnly(true)
                .list()
        );
    }

    @Override
    public Tag update(Tag tag) {
        return tag;
    }

    @Override
    public boolean delete(Long id) {
        int updatedRows = doWithSessionTransaction(session -> session.createQuery(DELETE_TAG)
                .setParameter("id", id)
                .executeUpdate()
        );
        return updatedRows > 0;
    }

    @Override
    public void saveToGiftCertificate(Long giftCertificateId, Long tagId) {
        doWithSessionTransaction(session -> session.createSQLQuery(INSERT_GIFT_CERTIFICATE_TAG)
                .setParameter(1, giftCertificateId)
                .setParameter(2, tagId)
                .executeUpdate());
    }
}
