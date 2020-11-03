package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateExtractor;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.GiftCertificateQuery;
import com.epam.esm.util.QueryConditionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate " +
            "(name, description, price, create_date, last_update_date, duration) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_GIFT_CERTIFICATE = "SELECT gift_certificate.id, " +
            "gift_certificate.name, gift_certificate.description, gift_certificate.price, " +
            "gift_certificate.create_date, gift_certificate.last_update_date, gift_certificate.duration, " +
            "tag.id, tag.name " +
            "FROM gift_certificate_has_tag " +
            "INNER JOIN tag ON tag.id = gift_certificate_has_tag.tag_id " +
            "INNER JOIN gift_certificate ON gift_certificate.id = gift_certificate_has_tag.gift_certificate_id " +
            "WHERE gift_certificate.id = ?";
    private static final String SELECT_ALL_GIFT_CERTIFICATES = "SELECT gift_certificate.id, " +
            "gift_certificate.name, gift_certificate.description, gift_certificate.price, " +
            "gift_certificate.create_date, gift_certificate.last_update_date, gift_certificate.duration, " +
            "tag.id, tag.name " +
            "FROM gift_certificate_has_tag " +
            "INNER JOIN tag ON tag.id = gift_certificate_has_tag.tag_id " +
            "INNER JOIN gift_certificate ON gift_certificate.id = gift_certificate_has_tag.gift_certificate_id";
    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET " +
            "name = ?, description = ?, price = ?, create_date = ?, last_update_date = ?, duration = ? WHERE id = ?";
    private static final String DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String INSERT_GIFT_CERTIFICATE_ID_AND_TAG_ID = "INSERT INTO gift_certificate_has_tag " +
            "(gift_certificate_id, tag_id) VALUES (?, ?)";
    private static final String SELECT_ALL_GIFT_CERTIFICATES_BY_QUERY_PARAMS = "SELECT gift_certificate.id, " +
            "gift_certificate.name, gift_certificate.description, gift_certificate.price, " +
            "gift_certificate.create_date, gift_certificate.last_update_date, gift_certificate.duration, " +
            "tag.id, tag.name " +
            "FROM gift_certificate_has_tag " +
            "INNER JOIN tag ON tag.id = gift_certificate_has_tag.tag_id " +
            "INNER JOIN gift_certificate ON gift_certificate.id = gift_certificate_has_tag.gift_certificate_id";
    private static final String DELETE_GIFT_CERTIFICATE_TAGS = "DELETE FROM gift_certificate_has_tag " +
            "WHERE gift_certificate_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateExtractor giftCertificateExtractor;

    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateExtractor giftCertificateExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateExtractor = giftCertificateExtractor;
    }

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_GIFT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, giftCertificate.getName());
            ps.setString(2, giftCertificate.getDescription());
            ps.setBigDecimal(3, giftCertificate.getPrice());
            ps.setObject(4, giftCertificate.getCreateDate());
            ps.setObject(5, giftCertificate.getLastUpdateDate());
            ps.setInt(6, giftCertificate.getDuration());
            return ps;
        }, keyHolder);
        Optional.ofNullable(keyHolder.getKey()).map(Number::longValue).ifPresent(giftCertificate::setId);
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(
                SELECT_GIFT_CERTIFICATE,
                giftCertificateExtractor,
                id
        );
        return CollectionUtils.isEmpty(giftCertificates) ? Optional.empty()
                : Optional.ofNullable(giftCertificates.get(0));
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATES, giftCertificateExtractor);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                giftCertificate.getDuration(),
                giftCertificate.getId()
        );
        return giftCertificate;
    }

    @Override
    public boolean delete(Long id) {
        int updatedRows = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE, id);
        return updatedRows > 0;
    }

    @Override
    public void saveGiftCertificateTags(GiftCertificate giftCertificate) {
        Long giftCertificateId = giftCertificate.getId();
        List<Tag> tags = giftCertificate.getTags();
        tags.forEach(tag -> jdbcTemplate.update(INSERT_GIFT_CERTIFICATE_ID_AND_TAG_ID, giftCertificateId, tag.getId()));
    }

    @Override
    public List<GiftCertificate> findAllGiftCertificatesByQueryParams(GiftCertificateQuery giftCertificateQuery) {
        String condition = QueryConditionUtils.generateConditionByQueryParams(giftCertificateQuery);
        return jdbcTemplate.query(
                SELECT_ALL_GIFT_CERTIFICATES_BY_QUERY_PARAMS + condition,
                giftCertificateExtractor
        );
    }

    @Override
    public void deleteTagsFromGiftCertificateById(Long giftCertificateId) {
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_TAGS, giftCertificateId);
    }
}
