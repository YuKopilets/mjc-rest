package com.epam.esm.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum GiftCertificateOrderType {
    NAME(" ORDER BY gift_certificate.name"),
    DATE(" ORDER BY gift_certificate.create_date");

    private final String condition;

    public static String generateSortCondition(GiftCertificateQuery giftCertificateQuery) {
        StringBuilder condition = new StringBuilder();
        String order = giftCertificateQuery.getOrder();
        String orderType = "";
        if (!StringUtils.isEmpty(order) && "desc".equals(order)) {
            orderType = " DESC";
        }
        final String finalOrderType = orderType;
        Arrays.stream(GiftCertificateOrderType.values())
                .filter(value -> value.name().equalsIgnoreCase(giftCertificateQuery.getSort()))
                .map(GiftCertificateOrderType::getCondition)
                .forEach(c -> {
                    condition.append(c);
                    condition.append(finalOrderType);
                });
        return condition.toString();
    }
}
