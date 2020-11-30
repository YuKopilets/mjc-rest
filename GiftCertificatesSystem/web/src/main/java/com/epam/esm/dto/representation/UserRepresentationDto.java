package com.epam.esm.dto.representation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

/**
 * The {@code User representation dto} is dto for representation user in
 * the response with <i>HAL</i> format.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see RepresentationModel
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserRepresentationDto extends RepresentationModel<UserRepresentationDto> {
    private Long id;
    private String login;
}
