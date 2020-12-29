package com.epam.esm.controller.converter;

import com.epam.esm.controller.dto.representation.UserRepresentationDto;
import com.epam.esm.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * The {@code Order dto converter} converts user to representation dto.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see ModelMapper
 */
@Component
@RequiredArgsConstructor
public class UserDtoConverter {
    private final ModelMapper modelMapper;

    /**
     * Convert user to representation dto before preparing response.
     *
     * @param user the user
     * @return the user representation dto
     */
    public UserRepresentationDto convertToRepresentationDto(User user) {
        return modelMapper.map(user, UserRepresentationDto.class);
    }
}
