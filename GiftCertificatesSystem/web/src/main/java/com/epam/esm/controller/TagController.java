package com.epam.esm.controller;

import com.epam.esm.converter.TagDtoConverter;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

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
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagDtoConverter dtoConverter;

    @PostMapping
    public Tag createTag(@RequestBody @Valid TagDto dto) {
        Tag tag = dtoConverter.convertToTag(dto);
        return tagService.addTag(tag);
    }

    @GetMapping(value = "/{id}")
    public Tag getTagById(@PathVariable @Min(value = 1) long id) {
        return tagService.getTagById(id);
    }

    @GetMapping
    public List<Tag> getAllTags(
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(value = 1) int page,
            @RequestParam(name = "page_size", required = false, defaultValue = "15")
            @Min(value = 12) @Max(value = 25) int pageSize
    ) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return tagService.getAllTags(pageRequest);
    }

    @GetMapping("/most-used")
    public Tag getUsed() {
        return tagService.getMostWidelyUsedTag();
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTag(@PathVariable @Min(value = 1) long id) {
        tagService.removeTag(id);
    }
}
