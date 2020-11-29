package com.epam.esm.dto.representation;

import com.epam.esm.converter.JsonDurationSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
public class GiftCertificateRepresentationDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    @JsonSerialize(using = JsonDurationSerializer.class)
    private Duration duration;
    private Set<TagRepresentationDto> tags;
}
