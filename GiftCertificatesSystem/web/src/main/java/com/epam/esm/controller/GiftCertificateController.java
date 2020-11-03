package com.epam.esm.controller;

import com.epam.esm.util.GiftCertificateQuery;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateNotFoundResponseStatusException;
import com.epam.esm.exception.InvalidRequestedIdResponseStatusException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
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
            log.error("Failed to get gift certificate by id (gift certificate not found)", e);
            throw new GiftCertificateNotFoundResponseStatusException(e);
        } catch (InvalidRequestedIdServiceException e) {
            log.error("Failed to get gift certificate by id (invalid id value)", e);
            throw new InvalidRequestedIdResponseStatusException(e);
        }
    }

    @GetMapping
    public List<GiftCertificate> getGiftCertificates(
            @RequestParam(name = "tag_name", required = false) String tagName,
            @RequestParam(name = "part_of_name", required = false) String partOfName,
            @RequestParam(name = "part_of_description", required = false) String partOfDescription,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "order", required = false) String order
    ) {
        return giftCertificateService.getGiftCertificates(
                new GiftCertificateQuery(tagName, partOfName, partOfDescription, sort, order)
        );
    }

    @PutMapping
    public GiftCertificate updateGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        try {
            return giftCertificateService.updateGiftCertificate(giftCertificate);
        } catch (GiftCertificateNotFoundServiceException e) {
            log.error("Failed to update gift certificate (gift certificate not found)", e);
            throw new GiftCertificateNotFoundResponseStatusException(e);
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteGiftCertificate(@PathVariable long id) {
        try {
            giftCertificateService.removeGiftCertificate(id);
        } catch (InvalidRequestedIdServiceException e) {
            log.error("Failed to delete gift certificate by id (invalid id value)", e);
            throw new InvalidRequestedIdResponseStatusException(e);
        }
    }
}
