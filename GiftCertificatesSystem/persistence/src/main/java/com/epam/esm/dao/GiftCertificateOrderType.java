package com.epam.esm.dao;

import org.springframework.util.StringUtils;

public enum GiftCertificateOrderType {
    NAME(" ORDER BY gift_certificate.name"),
    DATE(" ORDER BY gift_certificate.create_date");

    private final String condition;

    GiftCertificateOrderType(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public static String getSortCondition(GiftCertificateQuery giftCertificateQuery) {
        String condition = "";
        for (GiftCertificateOrderType value : GiftCertificateOrderType.values()) {
            if (value.name().equalsIgnoreCase(giftCertificateQuery.getSort())) {
                condition += value.getCondition();
                String order = giftCertificateQuery.getOrder();
                if (!StringUtils.isEmpty(order) && order.equals("desc")) {
                    condition += " DESC";
                }
            }
        }
        return condition;
    }
}
