package com.epam.esm.controller;

import com.epam.esm.converter.GiftCertificateDtoConverter;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.dto.GiftCertificatePatchDto;
import com.epam.esm.dto.GiftCertificatePostDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.GiftCertificateQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Gift certificate controller.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see RestController
 */
@RestController
@RequestMapping("/certificates")
@Validated
@Api(value = "/certificates", tags = "Gift certificate operations")
@RequiredArgsConstructor
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateDtoConverter dtoConverter;

    @PostMapping
    @ApiOperation(value = "add new certificate")
    public GiftCertificate createGiftCertificate(@RequestBody @Valid GiftCertificatePostDto dto) {
        GiftCertificate giftCertificate = dtoConverter.convertToGiftCertificate(dto);
        return giftCertificateService.addGiftCertificate(giftCertificate);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "get certificate by id")
    public GiftCertificate getGiftCertificateById(@PathVariable @Min(value = 1) long id) {
        return giftCertificateService.getGiftCertificateById(id);
    }

    @GetMapping
    @ApiOperation(value = "get list of certificates")
    public List<GiftCertificate> getGiftCertificates(
            @RequestParam(name = "tag_name", required = false) String[] tagNames,
            @RequestParam(name = "part_of_name", required = false) String partOfName,
            @RequestParam(name = "part_of_description", required = false) String partOfDescription,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "order", required = false) String order,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(value = 1) int page,
            @RequestParam(name = "page_size", required = false, defaultValue = "8")
            @Min(value = 6) @Max(value = 16) int pageSize
    ) {
        GiftCertificateQuery query = prepareGiftCertificateQuery(tagNames, partOfName, partOfDescription, sort, order);
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return giftCertificateService.getGiftCertificates(query, pageRequest);
    }

    @PatchMapping(value = "/{id}")
    @ApiOperation(value = "update certificate")
    public GiftCertificate updateGiftCertificate(@PathVariable @Min(value = 1) long id,
                                                 @RequestBody GiftCertificatePatchDto dto) {
        GiftCertificate giftCertificate = dtoConverter.convertToGiftCertificate(dto, id);
        return giftCertificateService.updateGiftCertificate(giftCertificate);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "delete certificate")
    public void deleteGiftCertificate(@PathVariable @Min(value = 1) long id) {
        giftCertificateService.removeGiftCertificate(id);
    }

    private GiftCertificateQuery prepareGiftCertificateQuery(String[] tagNames, String partOfName,
                                                             String partOfDescription, String sort, String order) {
        Set<String> names = new HashSet<>();
        if (tagNames != null) {
            Collections.addAll(names, tagNames);
        }
        return new GiftCertificateQuery(names, partOfName, partOfDescription, sort, order);
    }
}
