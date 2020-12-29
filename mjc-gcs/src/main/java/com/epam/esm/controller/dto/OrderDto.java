package com.epam.esm.controller.dto;

import com.epam.esm.entity.GiftCertificate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * The {@code Order dto} is dto for transferring order
 * data after request with <b>POST</b> http method.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    List<GiftCertificate> giftCertificates;

    public List<GiftCertificate> getGiftCertificates() {
        return Collections.unmodifiableList(giftCertificates);
    }
}
