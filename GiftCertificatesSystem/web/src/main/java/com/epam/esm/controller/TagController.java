package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping
    public Tag createTag(@RequestBody @Valid TagDto dto) {
        Tag tag = Tag.builder()
                .name(dto.getName())
                .build();
        return tagService.addTag(tag);
    }

    @GetMapping(value = "/{id}")
    public Tag getTagById(@PathVariable long id) {
        return tagService.getTagById(id);
    }

    @GetMapping
    public List<Tag> getAllTags(@RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        return tagService.getAllTags(page);
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteTag(@PathVariable long id) {
        tagService.removeTag(id);
        return HttpStatus.OK;
    }
}
