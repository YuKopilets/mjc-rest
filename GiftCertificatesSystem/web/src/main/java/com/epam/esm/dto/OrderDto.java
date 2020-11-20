package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import lombok.Value;

import java.util.Collections;
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
    Long userId;
    List<GiftCertificate> giftCertificates;

    public List<GiftCertificate> getGiftCertificates() {
        return Collections.unmodifiableList(giftCertificates);
    }
}
