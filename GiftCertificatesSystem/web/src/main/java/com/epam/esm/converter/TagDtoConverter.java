package com.epam.esm.converter;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagDtoConverter {
    private final ModelMapper modelMapper;

    public Tag convertToTag(TagDto dto) {
        return modelMapper.map(dto, Tag.class);
    }
}
