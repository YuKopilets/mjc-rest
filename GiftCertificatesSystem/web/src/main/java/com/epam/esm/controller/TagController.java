package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.ServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.addTag(tag);
    }

    @GetMapping(value = "/{id}")
    public Tag getTagById(@PathVariable long id) throws ServiceException {
        return tagService.getTagById(id);
    }

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @DeleteMapping
    public void deleteTag(@RequestBody Tag tag) throws ServiceException {
        tagService.removeTag(tag.getId());
    }
}
