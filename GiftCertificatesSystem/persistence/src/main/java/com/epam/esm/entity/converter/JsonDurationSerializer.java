package com.epam.esm.entity.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Duration;

/**
 * {@code JsonDurationSerializer} serializes Duration values to json content.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see JsonSerializer
 */
public class JsonDurationSerializer extends JsonSerializer<Duration> {
    private static final String DAY = " day";
    private static final String DAYS = " days";

    @Override
    public void serialize(Duration value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        long days = value.toDays();
        if (days == 1) {
            gen.writeString(days + DAY);
        } else {
            gen.writeString(days + DAYS);
        }
    }
}
