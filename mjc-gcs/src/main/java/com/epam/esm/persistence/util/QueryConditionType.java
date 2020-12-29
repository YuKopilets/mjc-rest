package com.epam.esm.persistence.util;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.function.UnaryOperator;

/**
 * The enum {@code Query condition type} prepare <i>WHERE</i> conditions by
 * query params.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@RequiredArgsConstructor
public enum QueryConditionType {
    TAG_NAME("t.name = ''{0}''"),
    PART_OF_NAME("gc.name LIKE ''%{0}%''"),
    PART_OF_DESCRIPTION("gc.description LIKE ''%{0}%''");

    private final String condition;

    /**
     * If defines a parameter as not empty, then adds it to the condition.
     *
     * @param param the param
     * @return the unary operator
     */
    public UnaryOperator<StringBuilder> generateCondition(String param) {
        return sb -> {
            if(StringUtils.isNotEmpty(param)) {
                sb.append(sb.length() > 0 ? defineConnectionOperator(sb) : QueryConditionConstant.WHERE);
                sb.append(MessageFormat.format(this.condition, param));
            }
            return sb;
        };
    }

    private String defineConnectionOperator(StringBuilder sb) {
        return sb.indexOf(QueryConditionConstant.TAG_NAME) != -1 && this == (TAG_NAME) ? QueryConditionConstant.OR
                : QueryConditionConstant.AND;
    }
}
