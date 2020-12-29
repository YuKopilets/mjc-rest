package com.epam.esm.controller.dto.representation;

import com.epam.esm.controller.converter.JsonDurationSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * The {@code Gift certificate representation dto} is dto for representation
 * gift certificate in the response.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
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
