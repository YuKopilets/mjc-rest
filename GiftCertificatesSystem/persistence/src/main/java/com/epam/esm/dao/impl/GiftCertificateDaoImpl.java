package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private final static String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate " +
            "(name, description, price, create_date, last_update_date, duration) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private final static String SELECT_GIFT_CERTIFICATE = "SELECT " +
            "id, name, description, price, create_date, last_update_date, duration " +
            "FROM gift_certificate WHERE id = ?";
    private final static String SELECT_ALL_GIFT_CERTIFICATES = "SELECT " +
            "id, name, description, price, create_date, last_update_date, duration " +
            "FROM gift_certificate";
    private final static String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET " +
            "name = ?, description = ?, price = ?, create_date = ?, last_update_date = ?, duration = ? WHERE id = ?";
    private final static String DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = ?";
    private final static String INSERT_GIFT_CERTIFICATE_ID_AND_TAG_ID = "INSERT INTO gift_certificate_has_tag " +
            "(gift_certificate_id, tag_id) VALUES (?, ?)";
    private final static String SELECT_ALL_TAGS_BY_GIFT_CERTIFICATE_ID = "SELECT gift_certificate_id, tag_id " +
            "FROM gift_certificate_has_tag WHERE gift_certificate_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;
    private final KeyHolder keyHolder;

    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        giftCertificateMapper = new GiftCertificateMapper();
        keyHolder = new GeneratedKeyHolder();
    }

    @Override
    public void save(GiftCertificate giftCertificate) {
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
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        GiftCertificate giftCertificate;
        try {
            giftCertificate = jdbcTemplate.queryForObject(SELECT_GIFT_CERTIFICATE, giftCertificateMapper, id);
        } catch (EmptyResultDataAccessException e) {
            giftCertificate = null;
        }
        return Optional.ofNullable(giftCertificate);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATES, giftCertificateMapper);
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                giftCertificate.getDuration(),
                giftCertificate.getId()
        );
    }

    @Override
    public boolean delete(Long id) {
        int updatedRows = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE, id);
        return updatedRows > 0;
    }

    @Override
    public void saveGiftCertificateIdAndTagId(Long giftCertificateId, Long tagId) {
        jdbcTemplate.update(INSERT_GIFT_CERTIFICATE_ID_AND_TAG_ID, giftCertificateId, tagId);
    }

    @Override
    public List<Tag> findAllTagsByGiftCertificateId(Long id) {
        // TODO
        return null;
    }
}
