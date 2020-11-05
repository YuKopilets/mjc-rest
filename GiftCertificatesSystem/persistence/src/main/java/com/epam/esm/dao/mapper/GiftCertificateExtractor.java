package com.epam.esm.dao.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GiftCertificateExtractor implements ResultSetExtractor<List<GiftCertificate>> {
    private static final String GIFT_CERTIFICATE_ID = "gift_certificate.id";
    private static final String GIFT_CERTIFICATE_NAME = "gift_certificate.name";
    private static final String GIFT_CERTIFICATE_DESCRIPTION = "gift_certificate.description";
    private static final String GIFT_CERTIFICATE_PRICE = "gift_certificate.price";
    private static final String GIFT_CERTIFICATE_CREATE_DATE = "gift_certificate.create_date";
    private static final String GIFT_CERTIFICATE_LAST_UPDATE_DATE = "gift_certificate.last_update_date";
    private static final String GIFT_CERTIFICATE_DURATION = "gift_certificate.duration";
    private static final String TAG_ID = "tag.id";
    private static final String TAG_NAME = "tag.name";

    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, GiftCertificate> giftCertificates = new HashMap<>();
        while (rs.next()) {
            GiftCertificate giftCertificate = getGiftCertificateFromRow(rs);
            Tag tag = getTagFromRow(rs);
            giftCertificates.putIfAbsent(giftCertificate.getId(), giftCertificate);
            giftCertificates.get(giftCertificate.getId()).getTags().add(tag);
        }
        return new ArrayList<>(giftCertificates.values());
    }

    private GiftCertificate getGiftCertificateFromRow(ResultSet rs) throws SQLException {
        return GiftCertificate.builder()
                .id(rs.getLong(GIFT_CERTIFICATE_ID))
                .name(rs.getString(GIFT_CERTIFICATE_NAME))
                .description(rs.getString(GIFT_CERTIFICATE_DESCRIPTION))
                .price(rs.getBigDecimal(GIFT_CERTIFICATE_PRICE))
                .createDate(rs.getObject(GIFT_CERTIFICATE_CREATE_DATE, LocalDateTime.class))
                .lastUpdateDate(rs.getObject(GIFT_CERTIFICATE_LAST_UPDATE_DATE, LocalDateTime.class))
                .duration(rs.getInt(GIFT_CERTIFICATE_DURATION))
                .tags(new ArrayList<>())
                .build();
    }

    private Tag getTagFromRow(ResultSet rs) throws SQLException {
        return Tag.builder()
                .id(rs.getLong(TAG_ID))
                .name(rs.getString(TAG_NAME))
                .build();
    }
}
