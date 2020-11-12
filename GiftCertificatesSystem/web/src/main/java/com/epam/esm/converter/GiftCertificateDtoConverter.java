package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificatePatchDto;
import com.epam.esm.dto.GiftCertificatePostDto;
import com.epam.esm.entity.GiftCertificate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class GiftCertificateDtoConverter {
    private final ModelMapper modelMapper;

    public GiftCertificate convertToGiftCertificate(GiftCertificatePostDto dto) {
        GiftCertificate giftCertificate = modelMapper.map(dto, GiftCertificate.class);
        giftCertificate.setDuration(Duration.ofDays(dto.getDuration()));
        return giftCertificate;
    }

    public GiftCertificate convertToGiftCertificate(GiftCertificatePatchDto dto, long id) {
        GiftCertificate giftCertificate = modelMapper.map(dto, GiftCertificate.class);
        giftCertificate.setId(id);
        giftCertificate.setDuration(Duration.ofDays(dto.getDuration()));
        return giftCertificate;
    }
}
