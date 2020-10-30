package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateNotFoundResponseStatusException;
import com.epam.esm.exception.InvalidRequestedIdResponseStatusException;
import com.epam.esm.service.query.GiftCertificateQuery;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public List<GiftCertificate> getAllGiftCertificates() {
        return giftCertificateService.getAllGiftCertificates();
    }

    @GetMapping("/filter")
    public List<GiftCertificate> getGiftCertificatesByQueryParams(
            @RequestParam(name = "tag_name", required = false) String tagName,
            @RequestParam(name = "part_of_name", required = false) String partOfName,
            @RequestParam(name = "part_of_description", required = false) String partOfDescription,
            @RequestParam(name = "sort_name", required = false) String sortName,
            @RequestParam(name = "sort_date", required = false) String sortDate
    ) {
        return giftCertificateService.getGiftCertificatesByQueryParams(
                new GiftCertificateQuery(
                        tagName,
                        partOfName,
                        partOfDescription,
                        sortName,
                        sortDate
                )
        );
    }

    @GetMapping(value = "/tag/{name}")
    public List<GiftCertificate> getAllGiftCertificatesByTagName(@PathVariable String name) {
        return giftCertificateService.getAllGiftCertificatesByTagName(name);
    }

    @GetMapping(value = "/{partOfName}")
    public List<GiftCertificate> getAllGiftCertificatesByPartOfName(@PathVariable String partOfName) {
        return giftCertificateService.getAllGiftCertificatesByPartOfName(partOfName);
    }

    @GetMapping(value = "/{partOfDescription}")
    public List<GiftCertificate> getAllGiftCertificatesByPartOfDescription(@PathVariable String partOfDescription) {
        return giftCertificateService.getAllGiftCertificatesByPartOfDescription(partOfDescription);
    }

    @GetMapping(value = "/sort/name/asc")
    public List<GiftCertificate> getGiftCertificatesByNameAsc() {
        return giftCertificateService.sortGiftCertificatesByNameAsc();
    }

    @GetMapping(value = "/sort/name/desc")
    public List<GiftCertificate> getGiftCertificatesByNameDesc() {
        return giftCertificateService.sortGiftCertificatesByNameDesc();
    }

    @GetMapping(value = "/sort/date/asc")
    public List<GiftCertificate> getGiftCertificatesByDateAsc() {
        return giftCertificateService.sortGiftCertificatesByDateAsc();
    }

    @GetMapping(value = "/sort/date/desc")
    public List<GiftCertificate> getGiftCertificatesByDateDesc() {
        return giftCertificateService.sortGiftCertificatesByDateDesc();
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
