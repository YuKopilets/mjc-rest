package com.epam.esm.util;

import lombok.experimental.UtilityClass;

import java.util.Set;

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
        Set<String> tagNames = query.getTagNames();

        tagNames.forEach(tagName -> QueryConditionType.TAG_NAME.generateCondition(tagName).apply(condition));
        QueryConditionType.PART_OF_NAME.generateCondition(query.getPartOfName()).apply(condition);
        QueryConditionType.PART_OF_DESCRIPTION.generateCondition(query.getPartOfDescription()).apply(condition);
        condition.append(QueryOrderType.generateSortCondition(query));

        setBracketsIntoCondition(condition);
        return condition.toString();
    }

    private static void setBracketsIntoCondition(StringBuilder condition) {
        if (conditionHasBothConnectOperators(condition)) {
            int startIndex = condition.indexOf(QueryConditionConstant.TAG_NAME);
            String temp = condition.substring(startIndex);
            String orCondition = temp.substring(0, temp.indexOf(QueryConditionConstant.AND));
            condition.replace(startIndex, startIndex + orCondition.length(), wrapToBrackets(orCondition));
        }
    }

    private static boolean conditionHasBothConnectOperators(StringBuilder condition) {
        return condition.indexOf(QueryConditionConstant.OR) != -1
                && condition.indexOf(QueryConditionConstant.AND) != -1;
    }

    private static String wrapToBrackets(String orCondition) {
        return QueryConditionConstant.OPEN_BRACKET + orCondition + QueryConditionConstant.CLOSE_BRACKET;
    }
}
