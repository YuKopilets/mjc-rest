package com.epam.esm.persistence.util;

import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.Set;

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
    Set<String> tagNames;
    String partOfName;
    String partOfDescription;
    @Setter
    @NonFinal
    String sort;
    String order;
}
