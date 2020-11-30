package com.epam.esm.converter;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.representation.TagRepresentationDto;
import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * The {@code Tag dto converter} converts dto to tag and conversely.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see ModelMapper
 */
@Component
@RequiredArgsConstructor
public class TagDtoConverter {
    private final ModelMapper modelMapper;

    /**
     * Convert dto to tag after request with <b>POST</b> http method.
     *
     * @param dto the dto
     * @return the tag
     */
    public Tag convertToTag(TagDto dto) {
        return modelMapper.map(dto, Tag.class);
    }

    /**
     * Convert tag to representation dto before preparing response.
     *
     * @param tag the tag
     * @return the tag representation dto
     */
    public TagRepresentationDto convertToRepresentationDto(Tag tag) {
        return modelMapper.map(tag, TagRepresentationDto.class);
    }

    /**
     * Convert tags to dto page for pagination.
     *
     * @param tags the tags
     * @return the page
     */
    public Page<TagRepresentationDto> convertTagsToDtoPage(Page<Tag> tags) {
        return tags.map(this::convertToRepresentationDto);
    }
}
