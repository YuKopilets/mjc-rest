package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.ServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @PostMapping
    public GiftCertificate createGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        return giftCertificateService.addGiftCertificate(giftCertificate);
    }

    @GetMapping(value = "/{id}")
    public GiftCertificate getGiftCertificateById(@PathVariable long id) throws ServiceException {
        return giftCertificateService.getGiftCertificateById(id);
    }

    @GetMapping
    public List<GiftCertificate> getAllGiftCertificates() {
        return giftCertificateService.getAllGiftCertificates();
    }

    @PutMapping
    public GiftCertificate updateGiftCertificate(@RequestBody GiftCertificate giftCertificate) throws ServiceException {
        return giftCertificateService.updateGiftCertificate(giftCertificate);
    }

    @DeleteMapping
    public void deleteGiftCertificate(@RequestBody GiftCertificate giftCertificate) throws ServiceException {
        giftCertificateService.removeGiftCertificate(giftCertificate.getId());
    }
}
