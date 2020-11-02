package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.InvalidRequestedIdResponseStatusException;
import com.epam.esm.exception.TagNotFoundResponseStatusException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import com.epam.esm.service.exception.TagNotFoundServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Slf4j
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
    public Tag getTagById(@PathVariable long id) {
        try {
            return tagService.getTagById(id);
        } catch (TagNotFoundServiceException e) {
            log.error("Failed to get tag by id (tag not found)", e);
            throw new TagNotFoundResponseStatusException(e);
        } catch (InvalidRequestedIdServiceException e) {
            log.error("Failed to get tag by id (invalid id value)", e);
            throw new InvalidRequestedIdResponseStatusException(e);
        }
    }

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTag(@PathVariable long id) {
        try {
            tagService.removeTag(id);
        } catch (InvalidRequestedIdServiceException e) {
            log.error("Failed to delete tag by id (invalid id value)", e);
            throw new InvalidRequestedIdResponseStatusException(e);
        }
    }
}
