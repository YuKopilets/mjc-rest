package com.epam.esm.dto;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.converter.JsonDurationDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Value;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Value
public class GiftCertificatePostDto {
    @NotNull
    @Size(min = 6, max = 255)
    @Pattern(regexp = "^[A-Za-z0-9]+(\\s[A-Za-z0-9]+)*$")
    String name;

    @NotNull
    @Size(min = 10)
    @Pattern(regexp = "^[A-Za-z0-9]+(\\s[A-Za-z0-9]+)*$")
    String description;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 10, fraction = 2)
    BigDecimal price;

    @NotNull
    @JsonDeserialize(using = JsonDurationDeserializer.class)
    Duration duration;

    @NotEmpty
    List<Tag> tags;
}
