package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificatePatchDto;
import com.epam.esm.dto.GiftCertificatePostDto;
import com.epam.esm.entity.GiftCertificate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * The {@code Gift certificate dto converter} converts dto to gift certificate.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see ModelMapper
 */
@Component
@RequiredArgsConstructor
public class GiftCertificateDtoConverter {
    private final ModelMapper modelMapper;

    /**
     * Convert dto to gift certificate after request
     * with <b>POST</b> http method.
     *
     * @param dto the dto
     * @return the gift certificate
     */
    public GiftCertificate convertToGiftCertificate(GiftCertificatePostDto dto) {
        GiftCertificate giftCertificate = modelMapper.map(dto, GiftCertificate.class);
        giftCertificate.setDuration(Duration.ofDays(dto.getDuration()));
        return giftCertificate;
    }

    /**
     * Convert dto to gift certificate after request
     * with <b>PATCH</b> http method.
     *
     * @param dto the dto
     * @param id  the gift certificate id
     * @return the gift certificate
     */
    public GiftCertificate convertToGiftCertificate(GiftCertificatePatchDto dto, long id) {
        GiftCertificate giftCertificate = modelMapper.map(dto, GiftCertificate.class);
        giftCertificate.setId(id);
        if (dto.getDuration() != null) {
            giftCertificate.setDuration(Duration.ofDays(dto.getDuration()));
        }
        return giftCertificate;
    }
}
