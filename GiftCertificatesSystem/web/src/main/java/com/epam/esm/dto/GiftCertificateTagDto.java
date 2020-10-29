package com.epam.esm.dto;

public class GiftCertificateTagDto {
    private Long giftCertificateId;
    private Long tagId;

    public GiftCertificateTagDto() {
    }

    public GiftCertificateTagDto(Long giftCertificateId, Long tagId) {
        this.giftCertificateId = giftCertificateId;
        this.tagId = tagId;
    }

    public Long getGiftCertificateId() {
        return giftCertificateId;
    }

    public Long getTagId() {
        return tagId;
    }
}
