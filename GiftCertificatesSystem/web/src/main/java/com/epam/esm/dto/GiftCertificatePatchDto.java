package com.epam.esm.dto;

import lombok.Value;

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
    String name;
    String description;
    BigDecimal price;
    Integer duration;
}
