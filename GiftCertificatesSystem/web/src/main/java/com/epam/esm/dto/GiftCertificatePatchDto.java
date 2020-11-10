package com.epam.esm.dto;

import com.epam.esm.entity.converter.JsonDurationDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Value;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Duration;

/**
 * The {@code Gift certificate patch dto} is dto for transferring gift
 * certificate data after request with <b>PATCH</b> http method.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Value
public class GiftCertificatePatchDto {
    @Size(min = 6, max = 255)
    @Pattern(regexp = "^[A-Za-z0-9]+(\\s[A-Za-z0-9]+)*$")
    String name;

    @Size(min = 10)
    @Pattern(regexp = "^[A-Za-z0-9]+(\\s[A-Za-z0-9]+)*$")
    String description;

    @PositiveOrZero
    @Digits(integer = 10, fraction = 2)
    BigDecimal price;

    @JsonDeserialize(using = JsonDurationDeserializer.class)
    Duration duration;
}
