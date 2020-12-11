package com.epam.esm.controller;

import com.epam.esm.converter.TagDtoConverter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.representation.TagRepresentationDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

/**
 * The type Tag controller.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see RestController
 */
@RestController
@RequestMapping("/tags")
@Validated
@Api(value = "/tags", tags = "Gift certificate's tag operations")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagDtoConverter dtoConverter;

    @PostMapping
    @ApiOperation(value = "add new tag")
    public TagRepresentationDto createTag(@RequestBody TagDto dto) {
        Tag tag = dtoConverter.convertToTag(dto);
        Tag addedTag = tagService.addTag(tag);
        return dtoConverter.convertToRepresentationDto(addedTag);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "get tag by id")
    public TagRepresentationDto getTagById(@PathVariable @Min(value = 1) long id) {
        Tag tag = tagService.getTagById(id);
        return dtoConverter.convertToRepresentationDto(tag);
    }

    @GetMapping
    @ApiOperation(value = "get list of tags")
    public Page<TagRepresentationDto> getAllTags(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Tag> tags = tagService.getAllTags(pageable);
        return dtoConverter.convertTagsToDtoPage(tags);
    }

    @GetMapping("/most-used")
    @ApiOperation(value = "get the most widely used tag")
    public TagRepresentationDto getUsed() {
        Tag tag = tagService.getMostWidelyUsedTag();
        return dtoConverter.convertToRepresentationDto(tag);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "delete tag")
    public void deleteTag(@PathVariable @Min(value = 1) long id) {
        tagService.removeTag(id);
    }
}
