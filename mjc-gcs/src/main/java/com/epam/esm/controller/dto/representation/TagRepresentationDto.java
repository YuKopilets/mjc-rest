package com.epam.esm.controller.dto.representation;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@code Tag representation dto} is dto for representation tag
 * in the response.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class TagRepresentationDto {
    private Long id;
    private String name;
    private String imageUrl;
}
