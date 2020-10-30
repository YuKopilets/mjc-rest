package com.epam.esm.dto;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NoArgsConstructor
public class GiftCertificateTagDto {
    @NonFinal
    private Long giftCertificateId;
    @NonFinal
    private Long tagId;
}
