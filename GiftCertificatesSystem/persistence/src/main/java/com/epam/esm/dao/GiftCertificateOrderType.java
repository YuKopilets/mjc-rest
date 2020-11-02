package com.epam.esm.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Getter
public enum GiftCertificateOrderType {
    NAME(" ORDER BY gift_certificate.name"),
    DATE(" ORDER BY gift_certificate.create_date");

    private final String condition;

    public static String getSortCondition(GiftCertificateQuery giftCertificateQuery) {
        StringBuilder condition = new StringBuilder();
        Arrays.stream(GiftCertificateOrderType.values())
                .filter(getOrderTypePredicate(giftCertificateQuery))
                .forEach(value -> {
                    condition.append(value.getCondition());
                    String order = giftCertificateQuery.getOrder();
                    if (!StringUtils.isEmpty(order) && "desc".equals(order)) {
                        condition.append(" DESC");
                    }
                });
        return condition.toString();
    }

    private static Predicate<GiftCertificateOrderType> getOrderTypePredicate(GiftCertificateQuery certificateQuery) {
        return value -> value.name().equalsIgnoreCase(certificateQuery.getSort());
    }
}
