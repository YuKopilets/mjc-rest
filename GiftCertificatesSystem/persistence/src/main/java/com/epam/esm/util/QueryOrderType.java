package com.epam.esm.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * The enum {@code Query order type} prepares <i>ORDER</i> conditions by sort
 * type param and order of sort param.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum QueryOrderType {
    DEFAULT(" ORDER BY gc.id"),
    NAME(" ORDER BY gc.name"),
    DATE(" ORDER BY gc.lastUpdateDate");

    private final String condition;

    /**
     * Find appropriate order type and generate <i>ORDER</i> condition.
     *
     * @param giftCertificateQuery the gift certificate query
     * @return the string
     */
    public static String generateSortCondition(GiftCertificateQuery giftCertificateQuery) {
        String orderType = generateOrderType(giftCertificateQuery.getOrder());
        return Arrays.stream(QueryOrderType.values())
                .filter(value -> value.name().equalsIgnoreCase(giftCertificateQuery.getSort()))
                .findFirst()
                .map(value -> value.getCondition() + orderType)
                .orElse(DEFAULT.getCondition() + orderType);
    }

    private static String generateOrderType(String order) {
        return StringUtils.isNotEmpty(order) && "asc".equals(order) ? " ASC" : " DESC";
    }
}
