package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate " +
            "(name, description, price, create_date, last_update_date, duration) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_GIFT_CERTIFICATE = "SELECT " +
            "id, name, description, price, create_date, last_update_date, duration " +
            "FROM gift_certificate WHERE id = ?";
    private static final String SELECT_ALL_GIFT_CERTIFICATES = "SELECT " +
            "id, name, description, price, create_date, last_update_date, duration " +
            "FROM gift_certificate";
    private static final String SELECT_ALL_TAGS_BY_GIFT_CERTIFICATE_ID = "SELECT tag.id, tag.name " +
            "FROM tag " +
            "INNER JOIN gift_certificate_has_tag ON gift_certificate_has_tag.tag_id = tag.id " +
            "WHERE gift_certificate_has_tag.gift_certificate_id = ?";
    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET " +
            "name = ?, description = ?, price = ?, create_date = ?, last_update_date = ?, duration = ? WHERE id = ?";
    private static final String DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String INSERT_GIFT_CERTIFICATE_ID_AND_TAG_ID = "INSERT INTO gift_certificate_has_tag " +
            "(gift_certificate_id, tag_id) VALUES (?, ?)";
    private static final String SELECT_ALL_GIFT_CERTIFICATES_BY_PART_OF_NAME = "SELECT " +
            "id, name, description, price, create_date, last_update_date, duration " +
            "FROM gift_certificate WHERE name LIKE '%?%'";
    private static final String SELECT_ALL_GIFT_CERTIFICATES_BY_PART_OF_DESCRIPTION = "SELECT " +
            "id, name, description, price, create_date, last_update_date, duration " +
            "FROM gift_certificate WHERE description LIKE '%?%'";
    private static final String SELECT_ALL_GIFT_CERTIFICATES_BY_TAG_NAME = "SELECT gift_certificate.id, " +
            "gift_certificate.name, gift_certificate.description, gift_certificate.price, " +
            "gift_certificate.create_date, gift_certificate.last_update_date, gift_certificate.duration " +
            "FROM gift_certificate_has_tag " +
            "INNER JOIN tag ON tag.id = gift_certificate_has_tag.tag_id " +
            "INNER JOIN gift_certificate ON gift_certificate.id = gift_certificate_has_tag.gift_certificate_id " +
            "WHERE tag.name = ?";
    private static final String DELETE_GIFT_CERTIFICATE_TAGS = "DELETE FROM gift_certificate_has_tag " +
            "WHERE gift_certificate_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;

    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
        this.tagMapper = tagMapper;
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
        giftCertificate.setId(keyHolder.getKey().longValue());
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        try {
            GiftCertificate giftCertificate = jdbcTemplate.queryForObject(
                    SELECT_GIFT_CERTIFICATE,
                    giftCertificateMapper,
                    id
            );
            List<Tag> tags = getGiftCertificateTags(giftCertificate.getId());
            giftCertificate.setTags(tags);
            return Optional.ofNullable(giftCertificate);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(
                SELECT_ALL_GIFT_CERTIFICATES,
                giftCertificateMapper
        );
        for (GiftCertificate giftCertificate : giftCertificates) {
            List<Tag> tags = getGiftCertificateTags(giftCertificate.getId());
            giftCertificate.setTags(tags);
        }
        return giftCertificates;
    }

    private List<Tag> getGiftCertificateTags(Long giftCertificateId) {
        return jdbcTemplate.query(SELECT_ALL_TAGS_BY_GIFT_CERTIFICATE_ID, tagMapper, giftCertificateId);
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
        for (Tag tag : tags) {
            jdbcTemplate.update(INSERT_GIFT_CERTIFICATE_ID_AND_TAG_ID, giftCertificateId, tag.getId());
        }
    }

    @Override
    public List<GiftCertificate> findAllGiftCertificatesByTagName(String name) {
        return jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATES_BY_TAG_NAME, giftCertificateMapper, name);
    }

    @Override
    public List<GiftCertificate> findAllGiftCertificatesByPartOfName(String name) {
        return jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATES_BY_PART_OF_NAME, giftCertificateMapper, name);
    }

    @Override
    public List<GiftCertificate> findAllGiftCertificatesByPartOfDescription(String description) {
        return jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATES_BY_PART_OF_DESCRIPTION,
                giftCertificateMapper,
                description
        );
    }

    @Override
    public void deleteGiftCertificateTagsByGiftCertificateId(Long giftCertificateId) {
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_TAGS, giftCertificateId);
    }
}
