package com.epam.esm.util;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public enum QueryConditionType {
    TAG_NAME("tag.name = '{0}'"),
    PART_OF_NAME("gift_certificate.name LIKE '%{0}%'"),
    PART_OF_DESCRIPTION("gift_certificate.description LIKE '%{0}%'");

    private final String condition;

    public UnaryOperator<StringBuilder> generateCondition(String param) {
        return sb -> {
            if(StringUtils.isNotEmpty(param)) {
                sb.append(sb.length() > 0 ? " AND " : " WHERE ");
                sb.append(MessageFormat.format(this.condition, param));
            }
            return sb;
        };
    }
}
