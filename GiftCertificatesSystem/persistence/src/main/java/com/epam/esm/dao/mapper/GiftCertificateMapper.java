package com.epam.esm.dao.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "name";
    private static final String PRICE = "name";
    private static final String CREATE_DATE = "name";
    private static final String LAST_UPDATE_DATE = "name";
    private static final String DURATION = "name";

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(rs.getLong(ID))
                .name(rs.getString(NAME))
                .description(rs.getString(DESCRIPTION))
                .price(rs.getBigDecimal(PRICE))
                .createDate(rs.getObject(CREATE_DATE, LocalDateTime.class))
                .lastUpdateDate(rs.getObject(LAST_UPDATE_DATE, LocalDateTime.class))
                .duration(rs.getInt(DURATION))
                .build();
        return giftCertificate;
    }
}
