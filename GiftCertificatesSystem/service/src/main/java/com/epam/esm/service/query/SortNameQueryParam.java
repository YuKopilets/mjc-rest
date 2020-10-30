package com.epam.esm.service.query;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;

import java.util.List;

public class SortNameQueryParam extends QueryParam {
    public SortNameQueryParam(GiftCertificateService giftCertificateService, GiftCertificateQuery giftCertificateQuery) {
        super(giftCertificateService, giftCertificateQuery);
    }

    @Override
    public void filterGiftCertificatesWithParam() {
        List<GiftCertificate> giftCertificates = giftCertificateQuery.getGiftCertificates();
        String sortName = giftCertificateQuery.getSortName();
        if (giftCertificates.isEmpty()) {
            List<GiftCertificate> giftCertificatesBySortedName;
            if (sortName.equals("asc")) {
                giftCertificatesBySortedName = giftCertificateService.sortGiftCertificatesByNameAsc();
            } else {
                giftCertificatesBySortedName = giftCertificateService.sortGiftCertificatesByNameDesc();
            }
            if (!giftCertificatesBySortedName.isEmpty()) {
                giftCertificateQuery.setGiftCertificates(giftCertificatesBySortedName);
                next();
            }
        } else {
            if (sortName.equals("asc")) {
                giftCertificates.sort((g1, g2) -> g1.getName().compareTo(g2.getName()));
            } else {
                giftCertificates.sort((g1, g2) -> g2.getName().compareTo(g1.getName()));
            }
            giftCertificateQuery.setGiftCertificates(giftCertificates);
            next();
        }
    }

    @Override
    protected boolean isEmptyParam() {
        return giftCertificateQuery.getSortName() == null;
    }
}
