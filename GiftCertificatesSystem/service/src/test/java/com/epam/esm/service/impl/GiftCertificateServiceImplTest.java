package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.context.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class GiftCertificateServiceImplTest {
    @Autowired
    private GiftCertificateService giftCertificateService;

    @Test
    void addGiftCertificate() {
    }

    @Test
    void getGiftCertificateById() {
    }

    @Test
    void getAllGiftCertificates() {
    }

    @Test
    void getAllGiftCertificatesByTagName() {
    }

    @Test
    void getAllGiftCertificatesByPartOfName() {
    }

    @Test
    void getAllGiftCertificatesByPartOfDescription() {
    }

    @Test
    void updateGiftCertificate() {
    }

    @Test
    void removeGiftCertificate() {
    }

    @Test
    void sortGiftCertificatesByNameAsc() {
    }

    @Test
    void sortGiftCertificatesByNameDesc() {
    }

    @Test
    void sortGiftCertificatesByDateAsc() {
    }

    @Test
    void sortGiftCertificatesByDateDesc() {
    }
}