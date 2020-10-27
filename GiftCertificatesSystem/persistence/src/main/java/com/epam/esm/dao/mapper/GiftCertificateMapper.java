package com.epam.esm.dao.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .price(rs.getBigDecimal("price"))
                .createDate(rs.getObject("create_date", LocalDateTime.class))
                .lastUpdateDate(rs.getObject("last_update_date", LocalDateTime.class))
                .duration(rs.getInt("duration"))
                .build();
        return giftCertificate;
    }
}
