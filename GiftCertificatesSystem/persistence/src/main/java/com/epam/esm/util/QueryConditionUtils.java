package com.epam.esm.util;

import lombok.experimental.UtilityClass;


/**
 * The {@code Query condition utils} is utility class for working with params of
 * <b>GET</b> http method.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@UtilityClass
public class QueryConditionUtils {
    /**
     * Generate condition for hql query to <i>gift_certificate</i> table
     * by query params.
     *
     * @param query the query params holder
     * @return the string
     */
    public static String generateConditionByQueryParams(GiftCertificateQuery query) {
        StringBuilder condition = new StringBuilder();
        QueryConditionType.TAG_NAME.generateCondition(query.getTagName()).apply(condition);
        QueryConditionType.PART_OF_NAME.generateCondition(query.getPartOfName()).apply(condition);
        QueryConditionType.PART_OF_DESCRIPTION.generateCondition(query.getPartOfDescription()).apply(condition);
        condition.append(QueryOrderType.generateSortCondition(query));
        return condition.toString();
    }
}
