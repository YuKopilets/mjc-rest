package com.epam.esm.util;

import org.springframework.util.StringUtils;

public final class QueryConditionUtils {
    private QueryConditionUtils() {
        throw new UnsupportedOperationException("Can't create object of utility class");
    }

    public static String generateConditionByQueryParams(GiftCertificateQuery giftCertificateQuery) {
        StringBuilder condition = new StringBuilder();
        if (!isEmptyConditionParam(condition, giftCertificateQuery.getTagName())) {
            condition.append("tag.name = '")
                    .append(giftCertificateQuery.getTagName())
                    .append("'");
        }
        if (!isEmptyConditionParam(condition, giftCertificateQuery.getPartOfName())) {
            condition.append("gift_certificate.name LIKE '%")
                    .append(giftCertificateQuery.getPartOfName())
                    .append("%'");
        }
        if (!isEmptyConditionParam(condition, giftCertificateQuery.getPartOfDescription())) {
            condition.append("gift_certificate.description LIKE '%")
                    .append(giftCertificateQuery.getPartOfDescription())
                    .append("%'");
        }
        if (!isEmptyParam(giftCertificateQuery.getSort())) {
            condition.append(GiftCertificateOrderType.generateSortCondition(giftCertificateQuery));
        }
        return condition.toString();
    }

    private static boolean isEmptyParam(String param) {
        return StringUtils.isEmpty(param);
    }

    private static boolean isEmptyConditionParam(StringBuilder condition, String param) {
        if (isEmptyParam(param)) {
            return true;
        } else {
            condition.append(condition.length() > 0 ? " AND " : " WHERE ");
            return false;
        }
    }
}
