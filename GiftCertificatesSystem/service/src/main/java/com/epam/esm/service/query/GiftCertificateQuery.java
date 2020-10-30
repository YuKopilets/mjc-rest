package com.epam.esm.service.query;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public class GiftCertificateQuery {
    private final String tagName;
    private final String partOfName;
    private final String partOfDescription;
    private final String sortName;
    private final String sortDate;
    private List<GiftCertificate> giftCertificates;

    public GiftCertificateQuery(String tagName, String partOfName, String partOfDescription, String sortName, String sortDate) {
        this.tagName = tagName;
        this.partOfName = partOfName;
        this.partOfDescription = partOfDescription;
        this.sortName = sortName;
        this.sortDate = sortDate;
    }

    public String getTagName() {
        return tagName;
    }

    public String getPartOfName() {
        return partOfName;
    }

    public String getPartOfDescription() {
        return partOfDescription;
    }

    public String getSortName() {
        return sortName;
    }

    public String getSortDate() {
        return sortDate;
    }

    public List<GiftCertificate> getGiftCertificates() {
        return giftCertificates;
    }

    public void setGiftCertificates(List<GiftCertificate> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }
}
