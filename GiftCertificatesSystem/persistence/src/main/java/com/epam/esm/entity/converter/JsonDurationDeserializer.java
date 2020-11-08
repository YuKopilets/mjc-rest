package com.epam.esm.entity.converter;

import com.epam.esm.exception.JsonDeserializeException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Duration;

/**
 * {@code JsonDurationDeserializer} deserializes json content to java
 * Duration value (based on days).
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see JsonDeserializer
 */
public class JsonDurationDeserializer extends JsonDeserializer<Duration> {
    @Override
    public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        long days = Long.parseLong(p.getText());
        if (days > 0) {
            return Duration.ofDays(days);
        } else {
            throw new JsonDeserializeException("Actual duration: " + days + ". Number of days can't be negative");
        }
    }
}
