package com.epam.esm.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum QueryOrderType {
    NAME(" ORDER BY gift_certificate.name"),
    DATE(" ORDER BY gift_certificate.create_date");

    private final String condition;

    public static String generateSortCondition(GiftCertificateQuery giftCertificateQuery) {
        String orderType = generateOrderType(giftCertificateQuery.getOrder());
        return Arrays.stream(QueryOrderType.values())
                .filter(value -> value.name().equalsIgnoreCase(giftCertificateQuery.getSort()))
                .findFirst()
                .map(value -> value.getCondition() + orderType)
                .orElse("");
    }

    private static String generateOrderType(String order) {
        if (StringUtils.isNotEmpty(order) && "desc".equals(order)) {
            return " DESC";
        } else {
            return "";
        }
    }
}
