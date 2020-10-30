package com.epam.esm.service.query;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;

import java.util.List;
import java.util.stream.Collectors;

public class PartOfNameQueryParam extends QueryParam {
    public PartOfNameQueryParam(GiftCertificateService giftCertificateService, GiftCertificateQuery giftCertificateQuery) {
        super(giftCertificateService, giftCertificateQuery);
    }

    @Override
    public void filterGiftCertificatesWithParam() {
        List<GiftCertificate> giftCertificates = giftCertificateQuery.getGiftCertificates();
        String partOfName = giftCertificateQuery.getPartOfName();
        if (giftCertificates.isEmpty()) {
            List<GiftCertificate> giftCertificatesByPartOfName =
                    giftCertificateService.getAllGiftCertificatesByPartOfName(partOfName);
            if (!giftCertificatesByPartOfName.isEmpty()) {
                giftCertificateQuery.setGiftCertificates(giftCertificatesByPartOfName);
                next();
            }
        } else {
            List<GiftCertificate> collectedGiftCertificates = giftCertificates.stream()
                    .filter(giftCertificate -> giftCertificate.getName().contains(partOfName))
                    .collect(Collectors.toList());
            if (!collectedGiftCertificates.isEmpty()) {
                giftCertificateQuery.setGiftCertificates(collectedGiftCertificates);
                next();
            }
        }
    }

    @Override
    protected boolean isEmptyParam() {
        return giftCertificateQuery.getPartOfName() == null;
    }
}
