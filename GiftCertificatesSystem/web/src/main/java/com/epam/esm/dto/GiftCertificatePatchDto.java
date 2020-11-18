package com.epam.esm.dto;

import lombok.Value;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

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
    @Pattern(regexp = "^[\\w]+(\\s[\\w]+)*$",
            message = "{certificate.name.contain}")
    String name;

    @Size(min = 10)
    @Pattern(regexp = "^[\\w]+(\\s[\\w]+)*$",
            message = "{certificate.description.contain}")
    String description;

    @PositiveOrZero
    @Digits(integer = 10, fraction = 2)
    BigDecimal price;

    @Min(value = 1)
    Integer duration;
}
