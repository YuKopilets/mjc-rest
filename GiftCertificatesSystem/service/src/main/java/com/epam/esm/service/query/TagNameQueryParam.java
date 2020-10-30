package com.epam.esm.service.query;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;

import java.util.List;

public class TagNameQueryParam extends QueryParam {
    public TagNameQueryParam(GiftCertificateService giftCertificateService, GiftCertificateQuery giftCertificateQuery) {
        super(giftCertificateService, giftCertificateQuery);
    }

    @Override
    public void filterGiftCertificatesWithParam() {
        List<GiftCertificate> giftCertificatesByTagName = giftCertificateService.getAllGiftCertificatesByTagName(
                giftCertificateQuery.getTagName()
        );
        if (!giftCertificatesByTagName.isEmpty()) {
            giftCertificateQuery.setGiftCertificates(giftCertificatesByTagName);
            next();
        }
    }

    @Override
    protected boolean isEmptyParam() {
        return giftCertificateQuery.getTagName() == null;
    }
}
