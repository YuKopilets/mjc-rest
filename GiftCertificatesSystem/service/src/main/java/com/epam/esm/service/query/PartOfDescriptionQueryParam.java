package com.epam.esm.service.query;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;

import java.util.List;
import java.util.stream.Collectors;

public class PartOfDescriptionQueryParam extends QueryParam {
    public PartOfDescriptionQueryParam(GiftCertificateService giftCertificateService, GiftCertificateQuery giftCertificateQuery) {
        super(giftCertificateService, giftCertificateQuery);
    }

    @Override
    public void filterGiftCertificatesWithParam() {
        List<GiftCertificate> giftCertificates = giftCertificateQuery.getGiftCertificates();
        String partOfDescription = giftCertificateQuery.getPartOfDescription();
        if (giftCertificates.isEmpty()) {
            List<GiftCertificate> giftCertificatesByPartOfDescription =
                    giftCertificateService.getAllGiftCertificatesByPartOfDescription(partOfDescription);
            if (!giftCertificatesByPartOfDescription.isEmpty()) {
                giftCertificateQuery.setGiftCertificates(giftCertificatesByPartOfDescription);
                next();
            }
        } else {
            List<GiftCertificate> collectedGiftCertificates = giftCertificates.stream()
                    .filter(giftCertificate -> giftCertificate.getDescription().contains(partOfDescription))
                    .collect(Collectors.toList());
            if (!collectedGiftCertificates.isEmpty()) {
                giftCertificateQuery.setGiftCertificates(collectedGiftCertificates);
                next();
            }
        }
    }

    @Override
    protected boolean isEmptyParam() {
        return giftCertificateQuery.getPartOfDescription() == null;
    }
}
