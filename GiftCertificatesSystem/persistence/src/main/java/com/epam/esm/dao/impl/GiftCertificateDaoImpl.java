package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
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
    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET " +
            "name = ?, description = ?, price = ?, create_date = ?, last_update_date = ?, duration = ? WHERE id = ?";
    private static final String DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String SELECT_ALL_GIFT_CERTIFICATES_BY_PART_OF_NAME = "SELECT " +
            "id, name, description, price, create_date, last_update_date, duration WHERE name LIKE '%?%'";
    private static final String SELECT_ALL_GIFT_CERTIFICATES_BY_PART_OF_DESCRIPTION = "SELECT " +
            "id, name, description, price, create_date, last_update_date, duration WHERE description LIKE '%?%'";
    private static final String SELECT_TAG_ID_BY_TAG_NAME = "SELECT id FROM tag WHERE name = ?";
    private static final String SELECT_ALL_GIFT_CERTIFICATE_BY_TAG_ID = "SELECT gift_certificate_id " +
            "FROM gift_certificate_has_tag WHERE tag_id = ?";

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
    public List<GiftCertificate> findAllGiftCertificatesByTagName(String name) {
        Long tagId;
        try {
            tagId = jdbcTemplate.queryForObject(SELECT_TAG_ID_BY_TAG_NAME, Long.class, name);
        } catch (EmptyResultDataAccessException e) {
            tagId = null;
        }
        ArrayList<GiftCertificate> giftCertificates = new ArrayList<>();
        if (tagId != null) {
            Long finalTagId = tagId;
            jdbcTemplate.query(connection -> {
                PreparedStatement ps = connection.prepareStatement(SELECT_ALL_GIFT_CERTIFICATE_BY_TAG_ID);
                ps.setLong(1, finalTagId);
                return ps;
            }, rs -> {
                Optional<GiftCertificate> giftCertificate = findById(rs.getLong("tag_id"));
                giftCertificate.ifPresent(giftCertificates::add);
            });
        }
        return giftCertificates;
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
}
