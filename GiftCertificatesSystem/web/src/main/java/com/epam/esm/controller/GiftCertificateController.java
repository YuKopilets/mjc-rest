package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificatePatchDto;
import com.epam.esm.dto.GiftCertificatePostDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.GiftCertificateQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Gift certificate controller.
 */
@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @PostMapping
    public GiftCertificate createGiftCertificate(@RequestBody @Valid GiftCertificatePostDto dto) {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .duration(dto.getDuration())
                .tags(dto.getTags())
                .build();
        return giftCertificateService.addGiftCertificate(giftCertificate);
    }

    @GetMapping(value = "/{id}")
    public GiftCertificate getGiftCertificateById(@PathVariable long id) {
        return giftCertificateService.getGiftCertificateById(id);
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

    @PatchMapping(value = "/{id}")
    public GiftCertificate updateGiftCertificate(@PathVariable long id, @RequestBody @Valid GiftCertificatePatchDto dto) {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(id)
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .duration(dto.getDuration())
                .build();
        return giftCertificateService.updateGiftCertificate(giftCertificate);
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteGiftCertificate(@PathVariable long id) {
        giftCertificateService.removeGiftCertificate(id);
        return HttpStatus.OK;
    }
}
