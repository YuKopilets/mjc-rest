package com.epam.esm.service.query;

import com.epam.esm.service.GiftCertificateService;

public abstract class QueryParam {
    protected final GiftCertificateService giftCertificateService;
    protected final GiftCertificateQuery giftCertificateQuery;
    private QueryParam nextQueryParam;

    public QueryParam(GiftCertificateService giftCertificateService, GiftCertificateQuery giftCertificateQuery) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateQuery = giftCertificateQuery;
    }

    public void setNextQueryParam(QueryParam nextQueryParam) {
        this.nextQueryParam = nextQueryParam;
    }

    public abstract void filterGiftCertificatesWithParam();

    protected abstract boolean isEmptyParam();

    protected void next() {
        if (nextQueryParam != null) {
            if (!nextQueryParam.isEmptyParam()) {
                nextQueryParam.filterGiftCertificatesWithParam();
            } else {
                nextQueryParam.next();
            }
        }
    }
}
