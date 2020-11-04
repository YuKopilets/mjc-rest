package com.epam.esm.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class QueryConditionUtils {
    public static String generateConditionByQueryParams(GiftCertificateQuery query) {
        StringBuilder condition = new StringBuilder();
        QueryConditionType.TAG_NAME.generateCondition(query.getTagName()).apply(condition);
        QueryConditionType.PART_OF_NAME.generateCondition(query.getPartOfName()).apply(condition);
        QueryConditionType.PART_OF_DESCRIPTION.generateCondition(query.getPartOfDescription()).apply(condition);
        condition.append(QueryOrderType.generateSortCondition(query));
        return condition.toString();
    }
}
