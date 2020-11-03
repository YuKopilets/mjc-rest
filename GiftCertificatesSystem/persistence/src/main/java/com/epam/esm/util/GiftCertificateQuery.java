package com.epam.esm.util;

import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
public class GiftCertificateQuery {
    String tagName;
    String partOfName;
    String partOfDescription;
    @Setter
    @NonFinal
    String sort;
    String order;
}
