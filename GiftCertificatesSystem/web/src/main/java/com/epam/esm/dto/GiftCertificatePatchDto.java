package com.epam.esm.dto;

import com.epam.esm.entity.Tag;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Set;

/**
 * The {@code Gift certificate patch dto} is dto for transferring gift
 * certificate data after request with <b>PATCH</b> http method.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Getter
public class GiftCertificatePatchDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private Set<Tag> tags;
}
