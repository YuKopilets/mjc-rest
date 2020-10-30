package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateNotFoundResponseStatusException;
import com.epam.esm.exception.InvalidRequestedIdResponseStatusException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
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
    public GiftCertificate getGiftCertificateById(@PathVariable long id) {
        try {
            return giftCertificateService.getGiftCertificateById(id);
        } catch (GiftCertificateNotFoundServiceException e) {
            throw new GiftCertificateNotFoundResponseStatusException(e);
        } catch (InvalidRequestedIdServiceException e) {
            throw new InvalidRequestedIdResponseStatusException(e);
        }
    }

    @GetMapping
    public List<GiftCertificate> getAllGiftCertificates() {
        return giftCertificateService.getAllGiftCertificates();
    }

    @GetMapping(value = "/tag/{name}")
    public List<GiftCertificate> getAllGiftCertificatesByTagName(@PathVariable String name) {
        return giftCertificateService.getAllGiftCertificatesByTagName(name);
    }

    @GetMapping("/{partOfName}")
    public List<GiftCertificate> getAllGiftCertificatesByPartOfName(@PathVariable String partOfName) {
        return giftCertificateService.getAllGiftCertificatesByPartOfName(partOfName);
    }

    @GetMapping("/{partOfDescription}")
    public List<GiftCertificate> getAllGiftCertificatesByPartOfDescription(@PathVariable String partOfDescription) {
        return giftCertificateService.getAllGiftCertificatesByPartOfDescription(partOfDescription);
    }

    @GetMapping("/sort/name/asc")
    public List<GiftCertificate> getGiftCertificatesByNameAsc() {
        return giftCertificateService.sortGiftCertificatesByNameAsc();
    }

    @GetMapping("/sort/name/desc")
    public List<GiftCertificate> getGiftCertificatesByNameDesc() {
        return giftCertificateService.sortGiftCertificatesByNameDesc();
    }

    @GetMapping("/sort/date/asc")
    public List<GiftCertificate> getGiftCertificatesByDateAsc() {
        return giftCertificateService.sortGiftCertificatesByDateAsc();
    }

    @GetMapping("/sort/date/desc")
    public List<GiftCertificate> getGiftCertificatesByDateDesc() {
        return giftCertificateService.sortGiftCertificatesByDateDesc();
    }

    @PutMapping
    public GiftCertificate updateGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        try {
            return giftCertificateService.updateGiftCertificate(giftCertificate);
        } catch (GiftCertificateNotFoundServiceException e) {
            throw new GiftCertificateNotFoundResponseStatusException(e);
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteGiftCertificate(@PathVariable long id) {
        try {
            giftCertificateService.removeGiftCertificate(id);
        } catch (InvalidRequestedIdServiceException e) {
            throw new InvalidRequestedIdResponseStatusException(e);
        }
    }
}
