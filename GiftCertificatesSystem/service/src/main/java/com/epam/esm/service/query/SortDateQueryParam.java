package com.epam.esm.service.query;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;

import java.util.List;

public class SortDateQueryParam extends QueryParam {
    public SortDateQueryParam(GiftCertificateService giftCertificateService, GiftCertificateQuery giftCertificateQuery) {
        super(giftCertificateService, giftCertificateQuery);
    }

    @Override
    public void filterGiftCertificatesWithParam() {
        List<GiftCertificate> giftCertificates = giftCertificateQuery.getGiftCertificates();
        String sortDate = giftCertificateQuery.getSortName();
        if (giftCertificates.isEmpty()) {
            List<GiftCertificate> giftCertificatesBySortedDate;
            if (sortDate.equals("asc")) {
                giftCertificatesBySortedDate = giftCertificateService.sortGiftCertificatesByDateAsc();
            } else {
                giftCertificatesBySortedDate = giftCertificateService.sortGiftCertificatesByDateDesc();
            }
            if (!giftCertificatesBySortedDate.isEmpty()) {
                giftCertificateQuery.setGiftCertificates(giftCertificatesBySortedDate);
                next();
            }
        } else {
            if (sortDate.equals("asc")) {
                giftCertificates.sort((g1, g2) -> g1.getCreateDate().compareTo(g2.getCreateDate()));
            } else {
                giftCertificates.sort((g1, g2) -> g2.getCreateDate().compareTo(g1.getCreateDate()));
            }
            giftCertificateQuery.setGiftCertificates(giftCertificates);
            next();
        }
    }

    @Override
    protected boolean isEmptyParam() {
        return giftCertificateQuery.getSortDate() == null;
    }
}
