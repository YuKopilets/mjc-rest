package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private final static String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_sertificate " +
            "(name, description, price, create_date, last_update_date, duration) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private final static String SELECT_GIFT_CERTIFICATE = "SELECT " +
            "(id, name, description, price, create_date, last_update_date, duration) " +
            "FROM gift_sertificate WHERE id = ?";
    private final static String SELECT_ALL_GIFT_CERTIFICATES = "SELECT " +
            "(id, name, description, price, create_date, last_update_date, duration) " +
            "FROM gift_sertificate";
    private final static String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_sertificate SET " +
            "name = ?, description = ?, price = ?, create_date = ?, last_update_date = ?, duration = ? WHERE id = ?";
    private final static String DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_sertificate WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;

    public GiftCertificateDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        giftCertificateMapper = new GiftCertificateMapper();
    }

    @Override
    public void save(GiftCertificate giftCertificate) {
        jdbcTemplate.update(INSERT_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                giftCertificate.getDuration()
        );
    }

    @Override
    public Optional<GiftCertificate> findGiftCertificateById(Long id) {
        Optional<GiftCertificate> giftCertificate = Optional.ofNullable(
                jdbcTemplate.queryForObject(SELECT_GIFT_CERTIFICATE, giftCertificateMapper, id)
        );
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificates() {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATES, giftCertificateMapper);
        return giftCertificates;
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
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE, id);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
