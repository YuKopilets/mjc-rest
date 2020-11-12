package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The {@code Order dto} is dto for transferring order
 * data after request with <b>POST</b> http method.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Value
public class OrderDto {
    @NotNull
    @Min(value = 1)
    Long userId;

    @NotEmpty
    List<GiftCertificate> giftCertificates;
}
