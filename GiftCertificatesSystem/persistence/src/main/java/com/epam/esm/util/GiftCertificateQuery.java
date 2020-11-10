package com.epam.esm.util;

import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

/**
 * The type Gift certificate query is immutable type for holding params of
 * <b>GET</b> http method for finding appropriate gift certificates.
 * {@code sort} can be change if the type of sort fails validation.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
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
