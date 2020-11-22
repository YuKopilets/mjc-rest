package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * The {@code Order representation dto} is dto for representation order in
 * response with <i>HAL</i> format.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see RepresentationModel
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderRepresentationDto extends RepresentationModel<OrderRepresentationDto> {
    private Long id;
    private Long userId;
    private BigDecimal cost;
    private LocalDateTime date;
    private List<GiftCertificate> giftCertificates;

    public List<GiftCertificate> getGiftCertificates() {
        return Collections.unmodifiableList(giftCertificates);
    }
}
