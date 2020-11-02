package com.epam.esm.dao;

import lombok.Value;

@Value
public class GiftCertificateQuery {
    String tagName;
    String partOfName;
    String partOfDescription;
    String sort;
    String order;
}
