package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserRepresentationDto extends RepresentationModel<UserRepresentationDto> {
    private Long id;
    private String login;
}
