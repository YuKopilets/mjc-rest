package com.epam.esm.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;

/**
 * Converter for representation duration in integer value in the database, but in
 * duration days type in entity attribute.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see AttributeConverter
 */
@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Duration attribute) {
        return (int)attribute.toDays();
    }

    @Override
    public Duration convertToEntityAttribute(Integer dbData) {
        return Duration.ofDays(dbData);
    }
}
